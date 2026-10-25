package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void redeclaredVariableThrows() throws Exception {
        IStmt decl = new VarDeclStmt("x", new IntType());
        decl.execute(state);
        assertThrows(StatementException.class, () -> decl.execute(state));
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        IStmt assign = new AssignStmt("x", new ValueExp(new IntValue(10)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void typeMismatchThrows() throws Exception {
        new VarDeclStmt("x", new IntType()).execute(state);
        IStmt assignBoolToInt = new AssignStmt("x", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> assignBoolToInt.execute(state));
    }

    @Test
    void nonBooleanIfConditionThrows() {
        IStmt ifStmt = new IfStmt(new ValueExp(new IntValue(1)), new NopStmt(), new NopStmt());
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}