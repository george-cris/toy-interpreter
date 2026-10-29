package interpreter.model.exp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.*;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.exceptions.*;
import interpreter.controller.Controller;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
class ArithExpTest {
 private IExp n(int n) { return new ValueExp(new IntValue(n)); }
 @ParameterizedTest @CsvSource({"ADD,7,3,10","SUB,7,3,4","MUL,7,3,21","DIV,7,3,2","DIV,-7,3,-2"})
 void computesEachOperator(ArithOp op,int a,int b,int expected) throws MyException { assertEquals(new IntValue(expected),new ArithExp(op,n(a),n(b)).eval(new MyDictionary<>())); }
 @Test void divisionByZeroThrows() { var e=new ArithExp(ArithOp.DIV,n(1),n(0)); assertEquals("division by zero",assertThrows(ExpressionException.class,()->e.eval(new MyDictionary<>())).getMessage()); }
 @Test void rejectsEitherNonIntegerOperand() { IExp b=new ValueExp(new BoolValue(true)); assertThrows(ExpressionException.class,()->new ArithExp(ArithOp.ADD,b,n(1)).eval(new MyDictionary<>())); assertThrows(ExpressionException.class,()->new ArithExp(ArithOp.ADD,n(1),b).eval(new MyDictionary<>())); }
 @Test void treeEncodesPrecedence() throws MyException { assertEquals(new IntValue(17),new ArithExp(ArithOp.ADD,n(2),new ArithExp(ArithOp.MUL,n(3),n(5))).eval(new MyDictionary<>())); }
}
