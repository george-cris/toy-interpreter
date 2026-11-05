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
class LogicExpTest {
 private IExp b(boolean b) { return new ValueExp(new BoolValue(b)); }
 @ParameterizedTest @CsvSource({"AND,true,true,true","AND,true,false,false","AND,false,true,false","AND,false,false,false","OR,true,true,true","OR,true,false,true","OR,false,true,true","OR,false,false,false"})
 void truthTable(LogicOp op,boolean a,boolean b,boolean expected) throws MyException { assertEquals(new BoolValue(expected),new LogicExp(op,b(a),b(b)).eval(new MyDictionary<>())); }
 @Test void rejectsEitherNonBooleanOperand() { IExp n=new ValueExp(new IntValue(1)); assertThrows(ExpressionException.class,()->new LogicExp(LogicOp.AND,n,b(true)).eval(new MyDictionary<>())); assertThrows(ExpressionException.class,()->new LogicExp(LogicOp.OR,b(true),n).eval(new MyDictionary<>())); }
 @Test void evaluatesRightOperandEvenWhenLeftDeterminesResult() { assertThrows(ExpressionException.class,()->new LogicExp(LogicOp.OR,b(true),new VarExp("missing")).eval(new MyDictionary<>())); assertThrows(ExpressionException.class,()->new LogicExp(LogicOp.AND,b(false),new VarExp("missing")).eval(new MyDictionary<>())); }
}
