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

    public static final IStmt EX4 = fileExample("test.in");

    public static final IStmt EX5 = sequence(
            new VarDeclStmt("a", new IntType()),
            new VarDeclStmt("b", new IntType()),
            new AssignStmt("a", new ValueExp(new IntValue(5))),
            new AssignStmt("b", new ValueExp(new IntValue(7))),
            new IfStmt(new RelationalExp(RelOp.LT, new VarExp("a"), new VarExp("b")),
                    new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b")))
    );

    public static IStmt fileExample(String fileName) {
        var file = new VarExp("varf");
        var value = new VarExp("varc");
        return sequence(
                new VarDeclStmt("varf", new StringType()),
                new AssignStmt("varf", new ValueExp(new StringValue(fileName))),
                new OpenRFileStmt(file),
                new VarDeclStmt("varc", new IntType()),
                new ReadFileStmt(file, "varc"),
                new PrintStmt(value),
                new ReadFileStmt(file, "varc"),
                new PrintStmt(value),
                new CloseRFileStmt(file)
        );
    }

    private static IStmt sequence(IStmt... statements) {
        IStmt program = statements[statements.length - 1];
        for (int i = statements.length - 2; i >= 0; i--) {
            program = new CompStmt(statements[i], program);
        }
        return program;
    }
    public static IStmt get(int number) {
        return switch (number) {
            case 1 -> EX1;
            case 2 -> EX2;
            case 3 -> EX3;
            case 4 -> EX4;
            case 5 -> EX5;
            default -> throw new IllegalArgumentException(
                    "Alege un exemplu intre 1 si 5."
            );
        };
    }
}