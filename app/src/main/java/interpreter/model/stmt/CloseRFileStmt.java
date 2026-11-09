package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Statement;

public record CloseRFileStmt(IExp exp) implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(exp.eval(state.getSymTable()) instanceof StringValue fileName)) throw new StatementException("closeRFile: file "+ exp +"is not of type string");
        var fileTable=state.getFileTable();
        if(!fileTable.isDefined(fileName)) throw new StatementException("closeRFile: file "+fileName+" is not open");
        BufferedReader reader= fileTable.lookup(fileName);
        try{
            reader.close();
            fileTable.remove(fileName);
        }catch(IOException e){
            throw new StatementException("closeRFile error: Cannot close "+fileName+"->"+e.getMessage());
        }
        return state;
    }
    @Override
    public String toString(){
        return "closeRFile("+exp+")";
    }
}
