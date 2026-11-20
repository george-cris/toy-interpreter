package interpreter;

import interpreter.view.Examples;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var input = new Scanner(System.in)) {
            var menu = new TextMenu(input, System.out);
            menu.addCommand(new ExitCommand("0", "Iesire", menu::stop));
            for (int number = 1; number <= 5; number++) {
                menu.addCommand(new RunExampleCommand(Integer.toString(number), Examples.get(number),
                        "log" + number + ".txt", System.out));
            }
            menu.show();
        }
    }
}
