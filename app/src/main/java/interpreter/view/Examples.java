package interpreter.view;

import interpreter.model.stmt.*;
import interpreter.model.exp.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

public final class Examples {

    private Examples() {}

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    public static final IStmt EX1 = new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                    new AssignStmt("v", num(2)),
                    new PrintStmt(new VarExp("v"))
            )
    );

    public static final IStmt EX2 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new VarDeclStmt("b", new IntType()),
                    new CompStmt(
                            new AssignStmt(
                                    "a",
                                    new ArithExp(
                                            ArithOp.ADD,
                                            num(2),
                                            new ArithExp(ArithOp.MUL, num(3), num(5))
                                    )
                            ),
                            new CompStmt(
                                    new AssignStmt(
                                            "b",
                                            new ArithExp(
                                                    ArithOp.ADD,
                                                    new VarExp("a"),
                                                    num(1)
                                            )
                                    ),
                                    new PrintStmt(new VarExp("b"))
                            )
                    )
            )
    );

    public static final IStmt EX3 = new CompStmt(
            new VarDeclStmt("a", new BoolType()),
            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(
                                    new IfStmt(
                                            new VarExp("a"),
                                            new AssignStmt("v", num(2)),
                                            new AssignStmt("v", num(3))
                                    ),
                                    new PrintStmt(new VarExp("v"))
                            )
                    )
            )
    );

    public static final IStmt EX4 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new AssignStmt("a", new ValueExp(new BoolValue(true))),
                    new PrintStmt(new VarExp("a"))
            )
    );

    public static IStmt get(int number) {
        return switch (number) {
            case 1 -> EX1;
            case 2 -> EX2;
            case 3 -> EX3;
            case 4 -> EX4;
            default -> throw new IllegalArgumentException(
                    "Alege un exemplu intre 1 si 4."
            );
        };
    }
}