package interpreter.controller;
import interpreter.model.PrgState;
import interpreter.repository.IRepository;
import interpreter.exceptions.*;
import java.io.PrintStream;
public class Controller {
    private final IRepository repo;
    private boolean displayFlag;
    private final PrintStream output;
    public Controller(IRepository repo,boolean displayFlag) { this(repo,displayFlag,System.out); }
    public Controller(IRepository repo,boolean displayFlag,PrintStream output) {
        this.repo=repo; this.displayFlag=displayFlag; this.output=output;
    }
    public void setDisplayFlag(boolean flag) { displayFlag=flag; }
    public PrgState oneStep(PrgState state) throws MyException {
        if (state.getExeStack().isEmpty()) throw new StatementException("program state stack is empty");
        return state.getExeStack().pop().execute(state);
    }
    public void allStep() throws MyException {
        PrgState state=repo.getCrtPrg();
        display(state);
        while (!state.getExeStack().isEmpty()) { oneStep(state); display(state); }
    }
    private void display(PrgState state) {
        if (displayFlag)
            output.println(state + "\n"); }
}
