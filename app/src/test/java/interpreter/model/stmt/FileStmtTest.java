package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStmtTest {
    @TempDir
    Path tempDir;

    private PrgState state;
    private Path filePath;

    @BeforeEach
    void setup() throws IOException {
        state = PrgState.of(new NopStmt());
        filePath = tempDir.resolve("test.in");
        Files.writeString(filePath, "15\n25\n");
    }

    @Test
    void openReadAndCloseFileWorkAsExpected() throws MyException {
        String key = filePath.toString();
        new VarDeclStmt("fileName", new StringType()).execute(state);
        new AssignStmt("fileName", new ValueExp(new StringValue(key))).execute(state);
        new VarDeclStmt("v", new IntType()).execute(state);

        new OpenRFileStmt(new ValueExp(new StringValue(key))).execute(state);
        assertTrue(state.getFileTable().isDefined(key));

        new ReadFileStmt(new ValueExp(new StringValue(key)), "v").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("v"));

        new ReadFileStmt(new ValueExp(new StringValue(key)), "v").execute(state);
        assertEquals(new IntValue(25), state.getSymTable().lookup("v"));

        new CloseRFileStmt(new ValueExp(new StringValue(key))).execute(state);
        assertFalse(state.getFileTable().isDefined(key));
    }
}