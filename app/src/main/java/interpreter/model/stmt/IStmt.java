package interpreter.model.stmt;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.type.IType;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public interface IStmt { PrgState execute(PrgState state) throws MyException; }
