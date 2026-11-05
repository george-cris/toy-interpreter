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
class MyListTest {
 @Test void preservesInsertionOrder() throws MyException { var l=new MyList<Integer>(); l.add(4); l.add(8); assertEquals(2,l.size()); assertEquals(4,l.get(0)); assertEquals(8,l.get(1)); }
 @Test void invalidIndicesThrow() { var l=new MyList<Integer>(); l.add(4); assertThrows(ADTException.class,()->l.get(-1)); assertThrows(ADTException.class,()->l.get(1)); }
}
