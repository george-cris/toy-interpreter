package interpreter.model.adt;
import java.util.ArrayList;
import java.util.List;
import interpreter.exceptions.ADTException;
public class MyList<T> implements IList<T> {
    private final List<T> data = new ArrayList<>();
    public void add(T value) { data.add(value); }
    public T get(int index) throws ADTException {
        if (index < 0 || index >= data.size()) throw new ADTException("list index out of bounds: " + index);
        return data.get(index);
    }
    public int size() { return data.size(); }
    public String toString() { return data.toString(); }
}
