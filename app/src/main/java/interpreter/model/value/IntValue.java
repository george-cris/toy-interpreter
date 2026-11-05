package interpreter.model.value;
import interpreter.model.type.*;
public final class IntValue implements IValue {
    private final int value;
    public IntValue(int value) { this.value = value; }
    public int getValue() { return value; }
    public IType getType() { return new IntType(); }
    public boolean equals(Object other) { return other instanceof IntValue && value == ((IntValue) other).value; }
    public int hashCode() { return Integer.hashCode(value); }
    public String toString() { return String.valueOf(value); }
}
