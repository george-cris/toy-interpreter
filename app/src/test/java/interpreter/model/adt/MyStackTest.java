package interpreter.model.adt;
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
class MyStackTest {
 @Test void popsInLifoOrder() throws MyException { var s=new MyStack<Integer>(); s.push(1); s.push(2); assertEquals(2,s.pop()); assertEquals(1,s.pop()); assertTrue(s.isEmpty()); }
 @Test void emptyPopThrows() { assertThrows(ADTException.class,()->new MyStack<>().pop()); }
}
