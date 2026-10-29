package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class ArithExp implements IExp {
    private final ArithOp op;
    private final IExp left, right;
    public ArithExp(ArithOp op,IExp left,IExp right) { this.op=op; this.left=left; this.right=right; }
    public IValue eval(IDictionary<String,IValue> table) throws MyException {
        IValue first = left.eval(table);
        if (!(first instanceof IntValue)) throw new ExpressionException("first operand is not an integer");
        IValue second = right.eval(table);
        if (!(second instanceof IntValue)) throw new ExpressionException("second operand is not an integer");
        int a = ((IntValue) first).getValue();
        int b = ((IntValue) second).getValue();
        return switch (op) {
            case ADD -> new IntValue(a + b);
            case SUB -> new IntValue(a - b);
            case MUL -> new IntValue(a * b);
            case DIV -> {
                if (b == 0) throw new ExpressionException("division by zero");
                yield new IntValue(a / b);
            }
        };
    }
    public String toString() { return "(" + left + " " + op + " " + right + ")"; }
}
