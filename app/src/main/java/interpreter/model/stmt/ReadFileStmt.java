package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(varName)) {
            throw new StatementException("readFile: variable " + varName + " is not declared");
        }
        IValue varVal = symTable.lookup(varName);
        if (!varVal.getType().equals(new IntType())) {
            throw new StatementException("readFile: variable " + varName + " is not of type int");
        }
        IValue val = exp.eval(symTable, state.getHeap());
        if (!(val instanceof StringValue fileName)) {
            throw new StatementException("readFile: " + exp + " is not a string");
        }
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        String key = fileName.val();
        if (!fileTable.isDefined(key)) {
            throw new StatementException("readFile: file " + key + " is not open");
        }
        BufferedReader reader = fileTable.lookup(key);
        try {
            String line = reader.readLine();
            int parsed = (line == null || line.isBlank()) ? 0 : Integer.parseInt(line.trim());
            symTable.update(varName, new IntValue(parsed));
        } catch (IOException e) {
            throw new StatementException("readFile: error reading file " + key + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("readFile: invalid integer in file " + key);
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}