package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class PrintStmt implements IStmt {
    private final IExp exp;
    public PrintStmt(IExp exp) { this.exp = exp; }
    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return state;
    }
    public String toString() { return "print(" + exp + ")"; }
}
