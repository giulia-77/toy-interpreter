package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WhileStatementTest {
    private PrgState state;

    @BeforeEach
    void setup() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ValueExp(new IntValue(0))).execute(state);
    }

    private void runUntilDone(IStmt stmt) throws MyException {
        state.getExeStack().push(stmt);
        while (!state.getExeStack().isEmpty()) {
            state.getExeStack().pop().execute(state);
        }
    }
//Ver execuția buclei când condiția e falsă din start (0 iterații), când se execută 1 dată
// și când se execută de mai multe ori (n iterații)
    @Test
    void whileRunsZeroOneAndMultipleTimes() throws MyException {
        runUntilDone(new WhileStmt(
                new ValueExp(new BoolValue(false)),
                new AssignStmt("v", new ValueExp(new IntValue(100)))
        ));
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));

        runUntilDone(new WhileStmt(
                new RelationalExp(RelOp.LT, new VarExp("v"), new ValueExp(new IntValue(1))),
                new AssignStmt("v", new ArithExp(ArithOp.ADD, new VarExp("v"), new ValueExp(new IntValue(1))))
        ));
        assertEquals(new IntValue(1), state.getSymTable().lookup("v"));

        runUntilDone(new WhileStmt(
                new RelationalExp(RelOp.LT, new VarExp("v"), new ValueExp(new IntValue(4))),
                new AssignStmt("v", new ArithExp(ArithOp.ADD, new VarExp("v"), new ValueExp(new IntValue(1))))
        ));
        assertEquals(new IntValue(4), state.getSymTable().lookup("v"));
    }

    @Test
    void whileWithNonBooleanConditionThrows() {
        assertThrows(StatementException.class,
                () -> new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt()).execute(state));
    }
}