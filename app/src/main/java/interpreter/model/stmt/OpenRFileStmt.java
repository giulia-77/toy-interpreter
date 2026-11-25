package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof StringValue fileName)) {
            throw new StatementException("openRFile: file " + exp + " is not of type string");
        }
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        String key = fileName.val();
        if (fileTable.isDefined(key)) {
            throw new StatementException("openRFile: file " + key + " is already open");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(key));
            fileTable.put(key, reader);
        } catch (IOException e) {
            throw new StatementException("openRFile: cannot open file " + key + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}