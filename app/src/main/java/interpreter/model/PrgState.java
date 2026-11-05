package interpreter.model;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String,IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;
    public PrgState(IStack<IStmt> stack, IDictionary<String,IValue> table, IList<IValue> out, IStmt program) {
        this.exeStack=stack; this.symTable=table; this.out=out; this.originalProgram=program;
        stack.push(program);
    }
    public IStack<IStmt> getExeStack() { return exeStack; }
    public void setExeStack(IStack<IStmt> stack) { exeStack=stack; }
    public IDictionary<String,IValue> getSymTable() { return symTable; }
    public void setSymTable(IDictionary<String,IValue> table) { symTable=table; }
    public IList<IValue> getOut() { return out; }
    public void setOut(IList<IValue> values) { out=values; }
    public IStmt getOriginalProgram() { return originalProgram; }
    public String toString() { return "ExeStack: " + exeStack + "\nSymTable: " + symTable + "\nOut: " + out; }
}
