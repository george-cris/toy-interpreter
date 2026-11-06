package interpreter.view;
import java.util.Scanner;
import java.io.PrintStream;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
public class TextMenu {
    private final Scanner input;
    private final PrintStream output;
    private IStmt selected;
    public TextMenu(Scanner input,PrintStream output) { this.input=input; this.output=output; }
    public void run() {
        while (true) {
            output.println("\n1. Selecteaza programul\n2. Ruleaza programul selectat\n0. Iesire");
            output.print("> ");
            if (!input.hasNextLine()) return;
            String option=input.nextLine().trim();
            try {
                switch(option) {
                    case "0": return;
                    case "1":
                        for(int i=1;i<=4;i++) output.println(i + ". " + Examples.get(i));
                        output.print("Exemplul: ");
                        if (!input.hasNextLine()) return;
                        selected=Examples.get(Integer.parseInt(input.nextLine().trim()));
                        output.println("Program selectat: " + selected);
                        break;
                    case "2":
                        if(selected==null) { output.println("Selecteaza mai intai un program."); break; }
                        // Each run gets fresh data; immutable statements can be reused safely.
                        PrgState state=new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),selected);
                        new Controller(new Repository(state),true,output).allStep();
                        output.println("Rezultat final: " + state.getOut());
                        break;
                    default: output.println("Optiune invalida.");
                }
            } catch(MyException | IllegalArgumentException e) { output.println("Eroare: " + e.getMessage()); }
        }
    }
}
