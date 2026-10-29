package interpreter.model.value;
import interpreter.model.type.*;
public final class BoolValue implements IValue {
    private final boolean value;
    public BoolValue(boolean value) { this.value = value; }
    public boolean getValue() { return value; }
    public IType getType() { return new BoolType(); }
    public boolean equals(Object other) { return other instanceof BoolValue && value == ((BoolValue) other).value; }
    public int hashCode() { return Boolean.hashCode(value); }
    public String toString() { return String.valueOf(value); }
}
