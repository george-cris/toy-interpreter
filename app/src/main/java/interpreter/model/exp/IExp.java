package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public interface IExp { IValue eval(IDictionary<String,IValue> symTable) throws MyException; }
