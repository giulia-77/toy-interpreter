package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private final List<PrgState> states;
    public Repository(PrgState initialProgram){
        this.states=new ArrayList<>();
        this.states.add(initialProgram);
    }
    public void addPrg(PrgState prg){
        states.add(prg);
    }
    @Override
    public PrgState getCrtPrg() {
        return states.get(0);
    }
}
