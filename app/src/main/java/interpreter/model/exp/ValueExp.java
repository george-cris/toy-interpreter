package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class ValueExp implements IExp {
    private final IValue value;
    public ValueExp(IValue value) { this.value = value; }
    public IValue eval(IDictionary<String,IValue> table) { return value; }
    public String toString() { return value.toString(); }
}
