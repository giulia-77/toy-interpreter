package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationalExpTest {
    private MyDictionary<String, IValue> symTable;
    private IExp five;
    private IExp ten;

    @BeforeEach
    void setup() {
        symTable = new MyDictionary<>();
        five = new ValueExp(new IntValue(5));
        ten = new ValueExp(new IntValue(10));
    }

    @Test
    void evaluatesAllRelationalOperators() throws MyException {
        var heap = new MyHeap();
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, five, ten).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, ten, five).eval(symTable, heap));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, five, five).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, ten, five).eval(symTable, heap));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, five, five).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, five, ten).eval(symTable, heap));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, five, ten).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, five, five).eval(symTable, heap));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, ten, five).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, five, ten).eval(symTable, heap));

        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, five, five).eval(symTable, heap));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, five, ten).eval(symTable, heap));
    }

    @Test
    void nonIntegerOperandThrowsExpressionException() {
        IExp strExp = new ValueExp(new StringValue("abc"));
        IExp intExp = new ValueExp(new IntValue(1));
        var heap = new MyHeap();
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, strExp, intExp).eval(symTable, heap));
        assertThrows(ExpressionException.class,
                () -> new RelationalExp(RelOp.LT, intExp, strExp).eval(symTable, heap));
    }
}