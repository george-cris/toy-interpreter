package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class VarDeclStmt implements IStmt {
    private final String name;
    private final IType type;
    public VarDeclStmt(String name, IType type) { this.name = name; this.type = type; }
    public PrgState execute(PrgState state) throws MyException {
        if (state.getSymTable().isDefined(name)) throw new StatementException("variable " + name + " already declared");
        state.getSymTable().put(name,type.defaultValue());
        return state;
    }
    public String toString() { return type + " " + name; }
}
