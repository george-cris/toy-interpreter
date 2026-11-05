package interpreter;
import java.util.Scanner;
import interpreter.view.TextMenu;
public class Main {
    public static void main(String[] args) { new TextMenu(new Scanner(System.in),System.out).run(); }
}
