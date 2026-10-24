package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.type.IType;

public record VarDeclStmt(String name, IType type){
    @Override
    public PrgState execute(PrgState state){

    }
    @Override public String toString() { return id + " = " + exp; }
}
