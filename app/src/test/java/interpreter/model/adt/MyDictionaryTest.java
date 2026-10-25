package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookup() throws ADTException {
        dict.put("a", 10);
        assertTrue(dict.isDefined("a"));
        assertEquals(10, dict.lookup("a"));
    }

    @Test
    void updateExistingKey() throws ADTException {
        dict.put("a", 10);
        dict.update("a", 20);
        assertEquals(20, dict.lookup("a"));
    }

    @Test
    void lookupMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("missing"));
    }

    @Test
    void updateMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("missing", 5));
    }
}