package interpreter.model.adt;
import java.util.HashMap;
import java.util.Map;
import interpreter.exceptions.ADTException;
public class MyDictionary<K,V> implements IDictionary<K,V> {
    private final Map<K,V> data = new HashMap<>();
    public boolean isDefined(K key) { return data.containsKey(key); }
    public V lookup(K key) throws ADTException {
        if (!isDefined(key)) throw new ADTException("missing key: " + key);
        return data.get(key);
    }
    public void put(K key,V value) { data.put(key,value); }
    public void update(K key,V value) throws ADTException {
        if (!isDefined(key)) throw new ADTException("missing key: " + key);
        data.put(key,value);
    }
    public String toString() { return data.toString(); }
}
