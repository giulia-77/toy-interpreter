package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReferenceTypesTest {

    @Test
    void refVariableGetsDefaultValueZeroAndLocationType() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);

        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void nestedRefTypeEqualsBehavior() {
        IType refRefIntA = new RefType(new RefType(new IntType()));
        IType refRefIntB = new RefType(new RefType(new IntType()));
        IType refInt = new RefType(new IntType());

        assertEquals(refRefIntA, refRefIntB);
        assertNotEquals(refRefIntA, refInt);
    }
}