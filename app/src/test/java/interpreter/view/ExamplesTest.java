package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExamplesTest {
    @TempDir
    Path directory;

    @Test
    void fileExamplePrintsItsTwoNumbers() throws Exception {
        Path file = Files.writeString(directory.resolve("numbers.in"), "15\n50\n");
        assertEquals(List.of(new IntValue(15), new IntValue(50)),
                run(Examples.fileExample(file.toString())));
    }

    @Test
    void fileExamplePrintsZerosForAnEmptyFile() throws Exception {
        Path file = Files.createFile(directory.resolve("empty.in"));
        assertEquals(List.of(new IntValue(0), new IntValue(0)),
                run(Examples.fileExample(file.toString())));
    }

    @Test
    void relationalExamplePrintsFive() throws Exception {
        assertEquals(List.of(new IntValue(5)), run(Examples.get(5)));
    }

    private List<IValue> run(IStmt program) throws Exception {
        var state = PrgState.of(program);
        new Controller(new Repository(state), false).allStep();
        return state.getOut().toList();
    }
}
