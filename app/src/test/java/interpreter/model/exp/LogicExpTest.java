package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setup() {
        symTable = new MyDictionary<>();
    }

    private IExp bool(boolean b) {
        return new ValueExp(new BoolValue(b));
    }

    @Test
    void evaluatesAndAndOrOperations() throws MyException {
        var heap = new MyHeap();
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, bool(true), bool(true)).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable, heap));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, bool(true), bool(false)).eval(symTable, heap));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, bool(false), bool(false)).eval(symTable, heap));
    }

    @Test
    void nonBooleanOperandThrowsExpressionException() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), bool(true));
        assertThrows(ExpressionException.class, () -> e.eval(symTable, new MyHeap()));
    }
}