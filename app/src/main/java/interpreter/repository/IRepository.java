package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.util.List;

public interface IRepository {
    void addPrg(PrgState prg);
    PrgState getCrtPrg();
    void logPrgStateExec(PrgState prgState) throws MyException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}
