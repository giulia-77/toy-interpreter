package interpreter.model.stmt;

import interpreter.model.exp.IExp;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS){

}

