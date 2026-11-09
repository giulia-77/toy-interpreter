package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prgList;
    private String logFilePath;
    public Repository(String logFilePath){
        this.prgList=new ArrayList<>();
        this.logFilePath=logFilePath;
    }
    @Override
    public void addPrg(PrgState prg){
        prgList.add(prg);
    }
    @Override
    public PrgState getCrtPrg() {
        if(prgList.isEmpty()){
            return null;
        }
        return prgList.get(0);
    }
    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)))) {
            logFile.println(prgState.toLogString());
        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.prgList = prgList;
    }
}
