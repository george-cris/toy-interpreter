package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class IfStmt implements IStmt {
    private final IExp cond;
    private final IStmt thenS;
    private final IStmt elseS;
    public IfStmt(IExp cond, IStmt thenS, IStmt elseS) { this.cond = cond; this.thenS = thenS; this.elseS = elseS; }
    public PrgState execute(PrgState state) throws MyException {
        IValue value = cond.eval(state.getSymTable());
        if (!(value instanceof BoolValue)) throw new StatementException("if condition is not a boolean");
        state.getExeStack().push(((BoolValue) value).getValue() ? thenS : elseS);
        return state;
    }
    public String toString() { return "if " + cond + " then " + thenS + " else " + elseS; }
}
