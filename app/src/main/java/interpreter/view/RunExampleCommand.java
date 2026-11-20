package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.io.PrintStream;

public final class RunExampleCommand extends Command {
    private final IStmt program;
    private final String logPath;
    private final PrintStream output;

    public RunExampleCommand(String key, IStmt program, String logPath, PrintStream output) {
        super(key, program.toString());
        this.program = program;
        this.logPath = logPath;
        this.output = output;
    }

    @Override
    public void execute() {
        var state = PrgState.of(program);
        var controller = new Controller(new Repository(state, logPath), false, output);
        try {
            controller.allStep();
            output.println("Rezultat final: " + state.getOut());
        } catch (MyException error) {
            output.println("Eroare: " + error.getMessage());
        }
    }
}
