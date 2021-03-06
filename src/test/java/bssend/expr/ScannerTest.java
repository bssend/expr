package bssend.expr;


import bssend.expr.parser.Lexer;
import bssend.expr.util.StringSequence;
import lombok.var;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static bssend.expr.TokenType.*;

public class ScannerTest {

    @Test
    public void test() throws Exception {
        var source = new StringSequence("2 + 1 + 4");
        var scanner = new Lexer();
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
