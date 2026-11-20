package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir;

    private PrgState state;
    private Path input;
    private StringValue name;
    private VarExp file;

    @BeforeEach
    void setUp() throws Exception {
        state = PrgState.of(new NopStmt());
        input = dir.resolve("numbers.in");
        name = new StringValue(input.toString());
        file = new VarExp("fileName");
        state.getSymTable().put("fileName", name);
        new VarDeclStmt("n", new IntType()).execute(state);
    }

    @AfterEach
    void closeRemainingReaders() throws IOException {
        for (BufferedReader reader : state.getFileTable().toMap().values()) {
            reader.close();
        }
    }

    @Test
    void reads_in_order_returns_zero_at_eof_and_closes() throws Exception {
        Files.writeString(input, " 15 \n50\n");
        new OpenRFileStmt(file).execute(state);
        BufferedReader reader = state.getFileTable().lookup(new StringValue(input.toString()));
        var read = new ReadFileStmt(file, "n");

        read.execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("n"));
        read.execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("n"));
        read.execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("n"));

        new CloseRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().toMap().isEmpty());
        assertThrows(IOException.class, reader::readLine);
    }

    @Test
    void open_rejects_missing_file_and_non_string() {
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(new ValueExp(new IntValue(1))).execute(state));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void duplicate_open_keeps_the_original_reader_and_its_position() throws Exception {
        Files.writeString(input, "15\n50\n");
        new OpenRFileStmt(file).execute(state);
        var read = new ReadFileStmt(file, "n");
        read.execute(state);

        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(new ValueExp(new StringValue(input.toString()))).execute(state));
        read.execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("n"));
    }

    @Test
    void invalid_read_target_does_not_consume_the_first_line() throws Exception {
        Files.writeString(input, "15\n");
        new OpenRFileStmt(file).execute(state);
        new VarDeclStmt("b", new BoolType()).execute(state);

        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "missing").execute(state));
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "b").execute(state));
        new ReadFileStmt(file, "n").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("n"));
    }

    @Test
    void read_and_close_reject_unopened_files_and_non_string_expressions() {
        var number = new ValueExp(new IntValue(1));

        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "n").execute(state));
        assertThrows(StatementException.class, () -> new ReadFileStmt(number, "n").execute(state));
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
        assertThrows(StatementException.class, () -> new CloseRFileStmt(number).execute(state));
    }

    @Test
    void invalid_line_and_read_error_leave_the_target_unchanged() throws Exception {
        Files.writeString(input, "15\nnot a number\n");
        new OpenRFileStmt(file).execute(state);
        var read = new ReadFileStmt(file, "n");
        read.execute(state);

        var parseError = assertThrows(StatementException.class, () -> read.execute(state));
        assertTrue(parseError.getMessage().contains("invalid integer"));
        assertEquals(new IntValue(15), state.getSymTable().lookup("n"));

        state.getFileTable().lookup(name).close();
        var ioError = assertThrows(StatementException.class, () -> read.execute(state));
        assertTrue(ioError.getMessage().contains("cannot read"));
        assertEquals(new IntValue(15), state.getSymTable().lookup("n"));
    }

    @Test
    void failed_close_reports_error_and_keeps_the_entry() throws Exception {
        try (var reader = new BufferedReader(new StringReader("")) {
            private boolean firstClose = true;

            @Override
            public void close() throws IOException {
                if (firstClose) {
                    firstClose = false;
                    throw new IOException("simulated close failure");
                }
                super.close();
            }
        }) {
            state.getFileTable().put(name, reader);

            var error = assertThrows(StatementException.class,
                    () -> new CloseRFileStmt(file).execute(state));
            assertTrue(error.getMessage().contains("cannot close"));
            assertSame(reader, state.getFileTable().lookup(name));
        }
    }
}
