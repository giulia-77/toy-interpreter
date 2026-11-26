package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapTest {
    //alocarea secventiala a adreselor
    @Test
    void allocateReturnsSequentialAddressesStartingFromOne() throws ADTException {
        IHeap heap = new MyHeap();

        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
        assertEquals(new IntValue(20), heap.get(2));
    }
    //updatarea unei adrese existente
    @Test
    void updateModifiesExistingAddress() throws ADTException {
        IHeap heap = new MyHeap();
        int addr = heap.allocate(new IntValue(10));

        heap.update(addr, new IntValue(99));
        assertEquals(new IntValue(99), heap.get(addr));
    }
    //arunca adt exception daca citim/updatam o adresa inexistenta in heap
    @Test
    void getAndUpdateMissingAddressThrow() {
        IHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.get(1));
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(5)));
    }
}