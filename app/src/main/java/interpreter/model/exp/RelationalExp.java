package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

public record RelationalExp(RelationalExp op, IExp left, IExp right) implements IExp{
    @Override public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof StringValue(String n1)))
            throw new ExpressionException("first operand is not a string");
        if (!(right.eval(symTable) instanceof StringValue(String n2)))
            throw new ExpressionException("second operand is not a string");
    }
}
