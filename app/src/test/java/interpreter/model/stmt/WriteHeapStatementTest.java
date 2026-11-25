package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WriteHeapStatementTest {
    private PrgState state;

    @BeforeEach
    void setup() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @Test
    void writeHeapUpdatesHeapLocation() throws MyException {
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void writeHeapErrorsAreThrown() throws MyException {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("missing", new ValueExp(new IntValue(10))).execute(state));
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(10))).execute(state));
        state.getSymTable().update("v", new RefValue(99, new IntType()));
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(10))).execute(state));
        state.getSymTable().update("v", new RefValue(1, new IntType()));
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(false))).execute(state));
    }
}