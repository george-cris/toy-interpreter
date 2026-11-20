package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(RelOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof IntValue a))
            throw new ExpressionException("first operand is not an integer");
        if (!(right.eval(symTable) instanceof IntValue b))
            throw new ExpressionException("second operand is not an integer");
        return new BoolValue(switch (op) {
            case LT -> a.getValue() < b.getValue();
            case LE -> a.getValue() <= b.getValue();
            case EQ -> a.getValue() == b.getValue();
            case NE -> a.getValue() != b.getValue();
            case GT -> a.getValue() > b.getValue();
            case GE -> a.getValue() >= b.getValue();
        });
    }
    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}
