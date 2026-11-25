package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setup() {
        symTable = new MyDictionary<>();
        symTable.put("a", new IntValue(10));
        symTable.put("b", new IntValue(7));
    }

    private IExp num(int v) {
        return new ValueExp(new IntValue(v));
    }

    @Test
    void evaluatesNestedExpressionWithVariables() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, new VarExp("a"), new VarExp("b"));
        assertEquals(new IntValue(17), e.eval(symTable, new MyHeap()));
    }

    @Test
    void evaluatesBasicArithmeticOperations() throws MyException {
        var heap = new MyHeap();
        assertEquals(new IntValue(5), new ArithExp(ArithOp.ADD, num(2), num(3)).eval(symTable, heap));
        assertEquals(new IntValue(1), new ArithExp(ArithOp.SUB, num(3), num(2)).eval(symTable, heap));
        assertEquals(new IntValue(6), new ArithExp(ArithOp.MUL, num(2), num(3)).eval(symTable, heap));
        assertEquals(new IntValue(2), new ArithExp(ArithOp.DIV, num(6), num(3)).eval(symTable, heap));
    }

    @Test
    void divisionByZeroThrowsExpressionException() {
        IExp e = new ArithExp(ArithOp.DIV, num(10), num(0));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable, new MyHeap()));
        assertTrue(ex.getMessage().toLowerCase().contains("zero"));
    }

    @Test
    void nonIntegerOperandThrowsExpressionException() {
        symTable.put("flag", new BoolValue(true));
        IExp e = new ArithExp(ArithOp.ADD, new VarExp("flag"), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable, new MyHeap()));
    }
}