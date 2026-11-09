package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable=fileTable;
        this.originalProgram = program;
        this.exeStack.push(program);
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable(){
        return fileTable;
    }
    public static PrgState of (IStmt program){
        return new PrgState(new MyStack<>(), new MyDictionary<>(),new MyList<>(), new MyDictionary<>(), program);
    }
    public String toLogString(){
        StringBuilder sb=new StringBuilder();
        sb.append("ExeStack:\n");
        for(IStmt s: exeStack.toList()){
            sb.append(s.toString()).append("\n");
        }
        sb.append("SymTable:\n");
        for(var entry: symTable.toMap().entrySet()){
            sb.append(entry.getKey()).append("->").append(entry.getValue()).append("\n");
        }
        sb.append("Output:\n");
        for(IValue v: out.toList()){
            sb.append(v.toString()).append("\n");
        }
        sb.append("FileTable:\n");
        for(StringValue f: fileTable.toMap().keySet()){
            sb.append(f.val()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "execution stack: " + exeStack + "\nsymbol table: " + symTable + "\noutput list: " + out + "\nfile table: "+fileTable+"\n";
    }
}