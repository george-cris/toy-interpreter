package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class VarExp implements IExp {
    private final String name;
    public VarExp(String name) { this.name = name; }
    public IValue eval(IDictionary<String,IValue> table) throws MyException {
        if (!table.isDefined(name)) throw new ExpressionException("variable " + name + " is not defined");
        return table.lookup(name);
    }
    public String toString() { return name; }
}
