package interpreter.model;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import java.io.BufferedReader;
public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String,IValue> symTable;
    private IList<IValue> out;
    private final IDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
    private final IStmt originalProgram;
    public PrgState(IStack<IStmt> stack, IDictionary<String,IValue> table, IList<IValue> out, IStmt program) {
        this.exeStack=stack; this.symTable=table; this.out=out; this.originalProgram=program;
        stack.push(program);
    }
    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), program);
    }
    public IDictionary<StringValue, BufferedReader> getFileTable() { return fileTable; }
    public IStack<IStmt> getExeStack() { return exeStack; }
    public void setExeStack(IStack<IStmt> stack) { exeStack=stack; }
    public IDictionary<String,IValue> getSymTable() { return symTable; }
    public void setSymTable(IDictionary<String,IValue> table) { symTable=table; }
    public IList<IValue> getOut() { return out; }
    public void setOut(IList<IValue> values) { out=values; }
    public IStmt getOriginalProgram() { return originalProgram; }
    public String toLogString() {
        var log = new StringBuilder("ExeStack:\n");
        exeStack.toList().forEach(statement -> log.append(statement).append('\n'));
        log.append("SymTable:\n");
        symTable.toMap().forEach((name, value) -> log.append(name).append(" --> ").append(value).append('\n'));
        log.append("Out:\n");
        out.toList().forEach(value -> log.append(value).append('\n'));
        log.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(file -> log.append(file.val()).append('\n'));
        return log.toString();
    }

    public String toString() { return "ExeStack: " + exeStack + "\nSymTable: " + symTable + "\nOut: " + out + "\nFileTable: " + fileTable.toMap().keySet(); }
}
