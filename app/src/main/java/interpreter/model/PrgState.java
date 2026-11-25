package interpreter.model;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;

import java.io.BufferedReader;
import java.util.TreeMap;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<String, BufferedReader> fileTable;
    private IHeap heap;
    private IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack,
                    IDictionary<String, IValue> symTable,
                    IList<IValue> out,
                    IDictionary<String, BufferedReader> fileTable,
                    IHeap heap,
                    IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program;
        this.exeStack.push(program);
    }

    public static PrgState of(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap(),
                program
        );
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap getHeap() {
        return heap;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public String logString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExeStack:\n").append(exeStack).append("\n");
        sb.append("SymTable:\n").append(symTable).append("\n");
        sb.append("Out:\n").append(out).append("\n");
        sb.append("FileTable:\n").append(fileTable).append("\n");
        sb.append("Heap:\n");
        new TreeMap<>(heap.getContent()).forEach((addr, val) ->
                sb.append(addr).append(" --> ").append(val).append("\n"));
        return sb.toString();
    }

    @Override
    public String toString() {
        return logString();
    }
}