package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    @Test
    void testAllSixOperatorsTrueAndFalse() throws MyException {
        var symTable = new MyDictionary<String, interpreter.model.value.IValue>();
        var five = new ValueExp(new IntValue(5));
        var ten = new ValueExp(new IntValue(10));

        // LT (<)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, five, ten).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, ten, five).eval(symTable));

        // LE (<=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, ten, five).eval(symTable));

        // EQ (==)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, five, ten).eval(symTable));

        // NE (!=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, five, ten).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, five, five).eval(symTable));

        // GT (>)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, ten, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, five, ten).eval(symTable));

        // GE (>=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, five, ten).eval(symTable));
    }

    @Test
    void testNonIntegerOperandThrows() {
        var symTable = new MyDictionary<String, interpreter.model.value.IValue>();
        var intExp = new ValueExp(new IntValue(5));
        var strExp = new ValueExp(new StringValue("5"));

        assertThrows(ExpressionException.class, () ->
                new RelationalExp(RelOp.LT, strExp, intExp).eval(symTable));
        assertThrows(ExpressionException.class, () ->
                new RelationalExp(RelOp.LT, intExp, strExp).eval(symTable));
    }
}