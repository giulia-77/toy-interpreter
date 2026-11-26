package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}
    //ia toate val din symTable, filtreaza doar cele de tip refValue si le colecteaza adresele
    //intr un set fara duplicate
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }
//păstrează în heap doar adresele care apar direct în symTableAddr
    //daca de ex am Ref Ref int a la adresa 2 si acolo se afla (1,int), adresa 1 nu e direct in symTable asa ca ar fi stearsa gresit
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Set<Integer> reachableAddresses(Set<Integer> roots, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(roots);
        boolean changed;
        //verifica fiecare adresa deja marcata in reachable si cauta valoarea ei în heap
        //daca valoarea stocats la acea adresa e un RefValue ia targetAddr
        //daca nu e deja in reachable, il pune în nextAddresses
        //daca s-au gasit adrese noi le adauga in reachable si repeta pana cand nu mai apare nimic nou
        do {
            changed = false;
            Set<Integer> nextAddresses = new HashSet<>();
            for (Integer addr : reachable) {
                IValue val = heap.get(addr);
                if (val instanceof RefValue refVal) {
                    int targetAddr = refVal.address();
                    if (!reachable.contains(targetAddr)) {
                        nextAddresses.add(targetAddr);
                    }
                }
            }
            if (!nextAddresses.isEmpty()) {
                reachable.addAll(nextAddresses);
                changed = true;
            }
        } while (changed);
        return reachable;
    }
//calculeaza toate adresele accesibile prin reachableAddresses(symTableAddr, heap)
// apoi filtreaza heap-ul pastrand doar intrarile a caror cheie (e.getKey()) se regaseste în reachable
    public static Map<Integer, IValue> safeGarbageCollector(Set<Integer> symTableAddr,
                                                            Map<Integer, IValue> heap) {
        Set<Integer> reachable = reachableAddresses(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}