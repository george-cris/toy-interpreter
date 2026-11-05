package interpreter.model.exp;
public enum LogicOp {
    AND("and"), OR("or");
    private final String symbol;
    LogicOp(String symbol) { this.symbol = symbol; }
    public String toString() { return symbol; }
}
