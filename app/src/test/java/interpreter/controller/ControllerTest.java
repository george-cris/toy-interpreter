package interpreter.controller;
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
import java.io.*;
class ControllerTest {
 private PrgState state(IStmt p) { return new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),p); }
 @Test void allExamplesHaveExpectedOutput() throws MyException { int[] expected={2,18,2}; for(int i=1;i<=3;i++) { var s=state(Examples.get(i)); new Controller(new Repository(s),false).allStep(); assertTrue(s.getExeStack().isEmpty()); assertEquals(1,s.getOut().size()); assertEquals(new IntValue(expected[i-1]),s.getOut().get(0)); } }
 @Test void emptyStackStepThrows() throws MyException { var s=state(new NopStmt()); var c=new Controller(new Repository(s),false); c.oneStep(s); assertThrows(StatementException.class,()->c.oneStep(s)); }
 @Test void exampleOneMatchesEveryDocumentedState() throws MyException {
   var s=state(Examples.EX1); var c=new Controller(new Repository(s),false);
   assertEquals("[(int v; (v = 2; print(v)))]",s.getExeStack().toString()); assertFalse(s.getSymTable().isDefined("v"));
   c.oneStep(s); assertEquals("[int v, (v = 2; print(v))]",s.getExeStack().toString()); assertFalse(s.getSymTable().isDefined("v"));
   c.oneStep(s); assertEquals("[(v = 2; print(v))]",s.getExeStack().toString()); assertEquals(new IntValue(0),s.getSymTable().lookup("v"));
   c.oneStep(s); assertEquals("[v = 2, print(v)]",s.getExeStack().toString()); assertEquals(new IntValue(0),s.getSymTable().lookup("v"));
   c.oneStep(s); assertEquals("[print(v)]",s.getExeStack().toString()); assertEquals(new IntValue(2),s.getSymTable().lookup("v")); assertEquals(0,s.getOut().size());
   c.oneStep(s); assertTrue(s.getExeStack().isEmpty()); assertEquals(new IntValue(2),s.getOut().get(0));
 }
 @Test void displayIncludesInitialAndFiveFollowingStates() throws MyException { var bytes=new ByteArrayOutputStream(); new Controller(new Repository(state(Examples.EX1)),true,new PrintStream(bytes)).allStep(); assertEquals(6,bytes.toString().split("ExeStack:",-1).length-1); }
 @Test void disabledDisplayWritesNothing() throws MyException { var bytes=new ByteArrayOutputStream(); new Controller(new Repository(state(Examples.EX1)),false,new PrintStream(bytes)).allStep(); assertEquals("",bytes.toString()); }
 @Test void immutableProgramCanRunWithFreshStates() throws MyException { var a=state(Examples.EX1); var b=state(Examples.EX1); new Controller(new Repository(a),false).allStep(); assertEquals(0,b.getOut().size()); assertFalse(b.getExeStack().isEmpty()); new Controller(new Repository(b),false).allStep(); assertEquals(a.getOut().get(0),b.getOut().get(0)); }
}
