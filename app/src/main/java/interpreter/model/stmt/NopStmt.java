package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class NopStmt implements IStmt {

    public NopStmt() {  }
    public PrgState execute(PrgState state) throws MyException {
        
        return state;
    }
    public String toString() { return "nop"; }
}
