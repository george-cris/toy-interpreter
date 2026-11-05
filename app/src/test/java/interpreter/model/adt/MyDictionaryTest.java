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
class MyDictionaryTest {
 @Test void storesLooksUpAndUpdates() throws MyException { var d=new MyDictionary<String,Integer>(); assertFalse(d.isDefined("a")); d.put("a",1); assertTrue(d.isDefined("a")); assertEquals(1,d.lookup("a")); d.update("a",2); assertEquals(2,d.lookup("a")); }
 @Test void missingLookupThrows() { assertThrows(ADTException.class,()->new MyDictionary<>().lookup("x")); }
 @Test void missingUpdateThrows() { assertThrows(ADTException.class,()->new MyDictionary<>().update("x",1)); }
}
