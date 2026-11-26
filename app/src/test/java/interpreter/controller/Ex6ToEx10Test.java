package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ex6ToEx10Test {

    private PrgState runProgram(IStmt stmt, String fileName) throws MyException {
        Path logPath = Path.of("build/test-logs", fileName);
        try {
            Files.createDirectories(logPath.getParent());
            Files.deleteIfExists(logPath);
        } catch (Exception ignored) {
        }
        PrgState prg = PrgState.of(stmt);
        IRepository repo = new Repository(prg, logPath.toString());
        new Controller(repo, false).allStep();
        return prg;
    }

    @Test
    void ex6OutputAndHeapAndSymTable() throws MyException {
        PrgState state = runProgram(Examples.ex6, "test-ex6.log");

        assertEquals(
                List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType()))),
                state.getOut().toList()
        );
        assertEquals(
                Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())),
                state.getHeap().getContent()
        );
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new RefValue(2, new RefType(new IntType())), state.getSymTable().lookup("a"));
    }

    @Test
    void ex7Output() throws MyException {
        PrgState state = runProgram(Examples.ex7, "test-ex7.log");

        assertEquals(
                List.of(new IntValue(20), new IntValue(25)),
                state.getOut().toList()
        );
    }

    @Test
    void ex8OutputAndHeapAfterWriteHeap() throws MyException {
        PrgState state = runProgram(Examples.ex8, "test-ex8.log");

        assertEquals(
                List.of(new IntValue(20), new IntValue(35)),
                state.getOut().toList()
        );
        assertEquals(new IntValue(30), state.getHeap().getContent().get(1));
    }

    @Test
    void ex9Prints20WithSafeGarbageCollector() throws MyException {
        PrgState state = runProgram(Examples.ex9, "test-ex9.log");

        assertEquals(
                List.of(new IntValue(20)),
                state.getOut().toList()
        );
    }

    @Test
    void ex10WhileLoopOutput() throws MyException {
        PrgState state = runProgram(Examples.ex10, "test-ex10.log");

        assertEquals(
                List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)),
                state.getOut().toList()
        );
    }
}