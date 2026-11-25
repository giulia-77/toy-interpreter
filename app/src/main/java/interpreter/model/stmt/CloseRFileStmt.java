package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = exp.eval(state.getSymTable(), state.getHeap());
        if (!(val instanceof StringValue fileName)) {
            throw new StatementException("closeRFile: file " + exp + " is not of type string");
        }
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        String key = fileName.val();
        if (!fileTable.isDefined(key)) {
            throw new StatementException("closeRFile: file " + key + " is not open");
        }
        BufferedReader reader = fileTable.lookup(key);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException("closeRFile: error closing file " + key + ": " + e.getMessage());
        }
        fileTable.remove(key);
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}