package interpreter.model.type;
import interpreter.model.value.*;
public final class BoolType implements IType {
    public IValue defaultValue() { return new BoolValue(false); }
    public boolean equals(Object other) { return other instanceof BoolType; }
    public int hashCode() { return BoolType.class.hashCode(); }
    public String toString() { return "bool"; }
}
