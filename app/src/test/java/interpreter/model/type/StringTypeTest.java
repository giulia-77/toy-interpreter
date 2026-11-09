package interpreter.model.type;

import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {

    @Test
    void testStringVariableGetsDefaultValue() {
        StringType type = new StringType();
        assertEquals(new StringValue(""), type.defaultValue());
    }

    @Test
    void testEqualStringValuesAreEqualAndHaveSameHashCode() {
        StringValue s1 = new StringValue("test");
        StringValue s2 = new StringValue("test");
        StringValue s3 = new StringValue("altul");

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, s3);
    }
}