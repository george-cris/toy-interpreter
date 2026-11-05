package interpreter.view;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import interpreter.model.*;
import interpreter.model.adt.*;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.exceptions.*;
import interpreter.controller.Controller;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import java.io.*;
import java.util.Scanner;
class TextMenuTest {
 @Test void handlesBadInputAndRepeatedRuns() { var bytes=new ByteArrayOutputStream(); new TextMenu(new Scanner("2\nwrong\n1\nabc\n1\n8\n1\n2\n2\n2\n0\n"),new PrintStream(bytes)).run(); String result=bytes.toString(); assertTrue(result.contains("Selecteaza mai intai")); assertTrue(result.contains("Optiune invalida")); assertTrue(result.contains("Eroare:")); assertEquals(2,result.split("Rezultat final: \\[18\\]",-1).length-1); }
 @Test void endOfInputExits() { assertDoesNotThrow(()->new TextMenu(new Scanner(""),new PrintStream(new ByteArrayOutputStream())).run()); }
}
