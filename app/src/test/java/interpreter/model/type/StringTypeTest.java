package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringTypeTest {
    @Test
    void string_variables_default_to_empty_and_accept_string_values() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("file", new StringType()).execute(state);
        assertEquals(new StringValue(""), state.getSymTable().lookup("file"));

        StringValue fileName = new StringValue("test.in");
        new AssignStmt("file", new ValueExp(fileName)).execute(state);
        assertEquals(fileName, state.getSymTable().lookup("file"));
    }

    @Test
    void string_values_with_equal_contents_can_be_used_as_the_same_key() throws MyException {
        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue(String.join(".", "test", "in"));

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());

        MyDictionary<StringValue, Integer> dictionary = new MyDictionary<>();
        dictionary.put(first, 15);
        assertEquals(15, dictionary.lookup(second));
    }
}
