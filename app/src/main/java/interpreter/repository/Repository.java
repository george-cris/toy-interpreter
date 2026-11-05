package interpreter.repository;
import interpreter.model.PrgState;
import java.util.List;
import java.util.ArrayList;
public class Repository implements IRepository {
    private final List<PrgState> states = new ArrayList<>();
    public Repository(PrgState state) { states.add(state); }
    public PrgState getCrtPrg() { return states.get(0); }
}
