package interpreter.model.stmt;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.AfterEach;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path dir;
    private PrgState state;
    private ValueExp fileExp;
    private Path filePath;

    @BeforeEach
    void setup() throws Exception {
        filePath = dir.resolve("numbers.in");
        Files.writeString(filePath, "7\n");
        fileExp = new ValueExp(new StringValue(filePath.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }
    @AfterEach
    void tearDown() throws Exception {
        for (var reader : state.getFileTable().toMap().values()) {
            reader.close();
        }
    }
    // openRFile tests
    @Test
    void openRFileAddsFileToFileTable() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        assertTrue(state.getFileTable().isDefined(new StringValue(filePath.toString())));
    }

    @Test
    void openRFileNonStringExpressionThrows() {
        var nonString = new ValueExp(new IntValue(123));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(nonString).execute(state));
    }

    @Test
    void openRFileAlreadyOpenThrows() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(fileExp).execute(state));
    }

    @Test
    void openRFileMissingFileThrows() {
        var missing = new ValueExp(new StringValue(dir.resolve("nonexistent.in").toString()));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missing).execute(state));
    }

    // readFile tests
    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        new ReadFileStmt(fileExp, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));

        new ReadFileStmt(fileExp, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readFileNotDeclaredVarThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(fileExp, "undeclared").execute(state));
    }

    @Test
    void readFileFileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(fileExp, "x").execute(state));
    }

    @Test
    void readFileNonIntegerLineThrows() throws Exception {
        Path invalidFile = dir.resolve("invalid.in");
        Files.writeString(invalidFile, "abc\n");
        ValueExp invalidFileExp = new ValueExp(new StringValue(invalidFile.toString()));

        new OpenRFileStmt(invalidFileExp).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(invalidFileExp, "x").execute(state));
    }

    // closeRFile tests
    @Test
    void closeRFileRemovesFileFromFileTable() throws MyException {
        new OpenRFileStmt(fileExp).execute(state);
        assertTrue(state.getFileTable().isDefined(new StringValue(filePath.toString())));

        new CloseRFileStmt(fileExp).execute(state);
        assertFalse(state.getFileTable().isDefined(new StringValue(filePath.toString())));
    }

    @Test
    void closeRFileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(fileExp).execute(state));
    }
    @Test
    void readFileVariableNotIntThrows() throws MyException {
        new VarDeclStmt("s", new StringType()).execute(state);
        new OpenRFileStmt(fileExp).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(fileExp, "s").execute(state));
    }
}