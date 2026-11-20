package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationalExpTest {
    @ParameterizedTest
    @CsvSource({
            "LT, 2, 3, true", "LT, 3, 3, false",
            "LE, 3, 3, true", "LE, 4, 3, false",
            "EQ, 3, 3, true", "EQ, 2, 3, false",
            "NE, 2, 3, true", "NE, 3, 3, false",
            "GT, 4, 3, true", "GT, 3, 3, false",
            "GE, 3, 3, true", "GE, 2, 3, false"
    })
    void compares_integers(RelOp op, int left, int right, boolean expected) throws MyException {
        IDictionary<String, IValue> table = new MyDictionary<>();
        table.put("left", new IntValue(left));
        IExp expression = new RelationalExp(op, new VarExp("left"), new ValueExp(new IntValue(right)));

        assertEquals(new BoolValue(expected), expression.eval(table));
    }

    @Test
    void rejects_non_integer_left_operand() {
        IExp expression = new RelationalExp(RelOp.LT,
                new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(3)));

        ExpressionException error = assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
        assertEquals("first operand is not an integer", error.getMessage());
    }

    @Test
    void rejects_non_integer_right_operand() {
        IExp expression = new RelationalExp(RelOp.EQ,
                new ValueExp(new IntValue(3)), new ValueExp(new StringValue("3")));

        ExpressionException error = assertThrows(ExpressionException.class,
                () -> expression.eval(new MyDictionary<>()));
        assertEquals("second operand is not an integer", error.getMessage());
    }
}
