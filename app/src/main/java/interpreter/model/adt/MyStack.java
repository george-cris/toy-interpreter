package interpreter.model.adt;
import java.util.ArrayDeque;
import java.util.Deque;
import interpreter.exceptions.ADTException;
public class MyStack<T> implements IStack<T> {
    private final Deque<T> data = new ArrayDeque<>();
    public void push(T value) { data.push(value); }
    public T pop() throws ADTException {
        if (data.isEmpty()) throw new ADTException("pop from an empty stack");
        return data.pop();
    }
    public boolean isEmpty() { return data.isEmpty(); }
    public String toString() { return data.toString(); }
}
