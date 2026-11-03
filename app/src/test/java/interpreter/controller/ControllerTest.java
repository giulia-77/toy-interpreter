package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static PrgState run(IStmt program) throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), program);
        Controller controller = new Controller(new Repository(state));
        controller.setDisplayFlag(false); // fara afisare la consola în timpul testelor
        controller.allStep();
        return state;
    }

    @Test
    void example1PrintTwo() throws MyException {
        PrgState state = run(Examples.ex1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintEighteen() throws MyException {
        PrgState state = run(Examples.ex2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintTwo() throws MyException {
        PrgState state = run(Examples.ex3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() {
        PrgState emptyState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        // scoatem NopStmt pus la initializare
        try {
            emptyState.getExeStack().pop();
        } catch (Exception ignored) {}

        Controller controller = new Controller(new Repository(emptyState));
        assertThrows(StatementException.class, () -> controller.oneStep(emptyState));
    }
}