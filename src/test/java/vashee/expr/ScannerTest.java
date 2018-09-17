package vashee.expr;


import lombok.var;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static vashee.expr.TokenType.*;

public class ScannerTest {

    @Test
    public void test() throws Exception {
        var source = new StringSequence("2 + 1 + 4");
        var scanner = new Scanner();
        var tokens = scanner.scan(source);

        assertEquals(tokens.next().getType(), is(INTEGER));
        assertEquals(tokens.next().getType(), is(WHITESPACE));
        assertEquals(tokens.next().getType(), is(ADD_OP));
        assertEquals(tokens.next().getType(), is(WHITESPACE));
        assertEquals(tokens.next().getType(), is(INTEGER));
        assertEquals(tokens.next().getType(), is(WHITESPACE));
        assertEquals(tokens.next().getType(), is(ADD_OP));
        assertEquals(tokens.next().getType(), is(WHITESPACE));
        assertEquals(tokens.next().getType(), is(INTEGER));
    }
}
