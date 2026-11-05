package interpreter.model.stmt;
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
class StatementTest {
 private PrgState state() throws MyException { var s=new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),new NopStmt()); s.getExeStack().pop(); return s; }
 private IExp n(int n) { return new ValueExp(new IntValue(n)); }
 @Test void compoundPushesFirstOnTop() throws MyException { var s=state(); IStmt a=new PrintStmt(n(1)),b=new PrintStmt(n(2)); new CompStmt(a,b).execute(s); assertSame(a,s.getExeStack().pop()); assertSame(b,s.getExeStack().pop()); }
 @Test void declarationsUseDefaults() throws MyException { var s=state(); new VarDeclStmt("i",new IntType()).execute(s); new VarDeclStmt("b",new BoolType()).execute(s); assertEquals(new IntValue(0),s.getSymTable().lookup("i")); assertEquals(new BoolValue(false),s.getSymTable().lookup("b")); }
 @Test void redeclarationThrowsAndPreservesValue() throws MyException { var s=state(); s.getSymTable().put("x",new IntValue(7)); assertThrows(StatementException.class,()->new VarDeclStmt("x",new BoolType()).execute(s)); assertEquals(new IntValue(7),s.getSymTable().lookup("x")); }
 @Test void assignmentUpdatesValue() throws MyException { var s=state(); new VarDeclStmt("x",new IntType()).execute(s); new AssignStmt("x",n(9)).execute(s); assertEquals(new IntValue(9),s.getSymTable().lookup("x")); }
 @Test void undeclaredAssignmentThrows() throws MyException { var s=state(); assertThrows(StatementException.class,()->new AssignStmt("x",n(9)).execute(s)); }
 @Test void mismatchedAssignmentThrowsAndPreservesValue() throws MyException { var s=state(); new VarDeclStmt("x",new BoolType()).execute(s); assertThrows(StatementException.class,()->new AssignStmt("x",n(9)).execute(s)); assertEquals(new BoolValue(false),s.getSymTable().lookup("x")); }
 @Test void printAppendsInOrder() throws MyException { var s=state(); new PrintStmt(n(2)).execute(s); new PrintStmt(n(3)).execute(s); assertEquals(2,s.getOut().size()); assertEquals(new IntValue(2),s.getOut().get(0)); assertEquals(new IntValue(3),s.getOut().get(1)); }
 @Test void printPropagatesEvaluationError() throws MyException { var s=state(); assertThrows(ExpressionException.class,()->new PrintStmt(new VarExp("missing")).execute(s)); assertEquals(0,s.getOut().size()); }
 @Test void ifSelectsOnlyCorrectBranch() throws MyException { for(boolean cond:new boolean[]{true,false}) { var s=state(); IStmt a=new NopStmt(), b=new PrintStmt(n(3)); new IfStmt(new ValueExp(new BoolValue(cond)),a,b).execute(s); assertSame(cond?a:b,s.getExeStack().pop()); assertTrue(s.getExeStack().isEmpty()); } }
 @Test void nonBooleanConditionThrows() throws MyException { var s=state(); assertThrows(StatementException.class,()->new IfStmt(n(1),new NopStmt(),new NopStmt()).execute(s)); }
 @Test void nopLeavesStateUnchanged() throws MyException { var s=state(); String before=s.toString(); assertSame(s,new NopStmt().execute(s)); assertEquals(before,s.toString()); }
}
