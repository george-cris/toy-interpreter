package interpreter.controller;

import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.CloseRFileStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.OpenRFileStmt;
import interpreter.model.value.StringValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionLogTest {
    @TempDir
    Path dir;

    @Test
    void appends_initial_and_each_step_in_the_required_format() throws Exception {
        Path logPath = dir.resolve("log1.txt");
        Files.writeString(logPath, "previous run\n\n");
        var state = PrgState.of(Examples.EX1);
        new Controller(new Repository(state, logPath.toString()), false).allStep();

        String[] entries = Files.readString(logPath).split("\n\n");
        assertEquals(7, entries.length);
        assertEquals("previous run", entries[0]);
        for (int i = 1; i < entries.length; i++) {
            var headings = entries[i].lines().filter(line -> line.endsWith(":")).toList();
            assertEquals(List.of("ExeStack:", "SymTable:", "Out:", "FileTable:"), headings);
        }
        assertEquals("ExeStack:\n" + Examples.EX1 + "\nSymTable:\nOut:\nFileTable:", entries[1]);
        assertTrue(entries[2].startsWith("ExeStack:\nint v\n(v = 2; print(v))\n"));
        assertTrue(entries[3].contains("SymTable:\nv --> 0\n"));
        assertEquals("ExeStack:\nSymTable:\nv --> 2\nOut:\n2\nFileTable:", entries[6]);
    }

    @Test
    void lists_the_file_name_while_the_file_is_open() throws Exception {
        Path input = Files.createFile(dir.resolve("numbers.in"));
        Path logPath = dir.resolve("log4.txt");
        var fileName = new ValueExp(new StringValue(input.toString()));
        var state = PrgState.of(new CompStmt(new OpenRFileStmt(fileName), new CloseRFileStmt(fileName)));
        new Controller(new Repository(state, logPath.toString()), false).allStep();

        String[] entries = Files.readString(logPath).split("\n\n");
        assertEquals(4, entries.length);
        assertTrue(entries[2].endsWith("FileTable:\n" + input));
        assertTrue(entries[3].endsWith("FileTable:"));
    }

    @Test
    void invalid_log_destination_is_reported_before_execution() {
        var state = PrgState.of(Examples.EX1);
        var controller = new Controller(new Repository(state, dir.toString()), false);

        var error = assertThrows(RepositoryException.class, controller::allStep);
        assertTrue(error.getMessage().contains("cannot write the log file " + dir));
        assertEquals(List.of(Examples.EX1), state.getExeStack().toList());
    }
}
