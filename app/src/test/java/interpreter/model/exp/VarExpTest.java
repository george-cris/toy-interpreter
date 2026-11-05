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
class VarExpTest {
 @Test void looksUpCurrentValueWithoutChangingTable() throws MyException { var t=new MyDictionary<String,IValue>(); t.put("x",new IntValue(5)); assertEquals(new IntValue(5),new VarExp("x").eval(t)); assertEquals(new IntValue(5),t.lookup("x")); }
 @Test void undefinedVariableThrows() { assertThrows(ExpressionException.class,()->new VarExp("x").eval(new MyDictionary<>())); }
 @Test void constantReturnsItsValue() throws MyException { assertEquals(new BoolValue(true),new ValueExp(new BoolValue(true)).eval(new MyDictionary<>())); }
}
