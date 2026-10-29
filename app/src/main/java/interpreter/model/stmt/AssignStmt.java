package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class AssignStmt implements IStmt {
    private final String id;
    private final IExp exp;
    public AssignStmt(String id, IExp exp) { this.id = id; this.exp = exp; }
    public PrgState execute(PrgState state) throws MyException {
        if (!state.getSymTable().isDefined(id)) throw new StatementException("variable " + id + " was not declared");
        IValue value = exp.eval(state.getSymTable());
        if (!value.getType().equals(state.getSymTable().lookup(id).getType()))
            throw new StatementException("declared type of variable " + id + " and type of the assigned expression do not match");
        state.getSymTable().update(id,value);
        return state;
    }
    public String toString() { return id + " = " + exp; }
}
