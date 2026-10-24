package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;

public record PrintStmt(IExp exp) {
    @Override
    public PrgState execute(PrgState state) throws MyException {

    }

}
