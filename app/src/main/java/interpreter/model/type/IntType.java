package interpreter.model.type;
import interpreter.model.value.*;
public final class IntType implements IType {
    public IValue defaultValue() { return new IntValue(0); }
    public boolean equals(Object other) { return other instanceof IntType; }
    public int hashCode() { return IntType.class.hashCode(); }
    public String toString() { return "int"; }
}
