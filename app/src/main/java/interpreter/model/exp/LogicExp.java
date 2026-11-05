package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.*;
public final class LogicExp implements IExp {
    private final LogicOp op;
    private final IExp left, right;
    public LogicExp(LogicOp op,IExp left,IExp right) { this.op=op; this.left=left; this.right=right; }
    public IValue eval(IDictionary<String,IValue> table) throws MyException {
        IValue first = left.eval(table);
        if (!(first instanceof BoolValue)) throw new ExpressionException("first operand is not a boolean");
        IValue second = right.eval(table);
        if (!(second instanceof BoolValue)) throw new ExpressionException("second operand is not a boolean");
        boolean a = ((BoolValue) first).getValue();
        boolean b = ((BoolValue) second).getValue();
        return new BoolValue(op == LogicOp.AND ? a && b : a || b);
    }
    public String toString() { return "(" + left + " " + op + " " + right + ")"; }
}
