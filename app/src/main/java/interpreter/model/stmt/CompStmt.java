package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;
    public CompStmt(IStmt first, IStmt second) { this.first = first; this.second = second; }
    public PrgState execute(PrgState state) throws MyException {
        state.getExeStack().push(second); state.getExeStack().push(first);
        return state;
    }
    public String toString() { return "(" + first + "; " + second + ")"; }
}
