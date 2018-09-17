package vashee.expr;

import lombok.var;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void test1() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("1 + 2 + 3");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        assertEquals(result.getValue(), 6);
    }

    @Test
    public void test2() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("1 + 2 * 3");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        assertEquals(result.getValue(), 7);
    }

    @Test
    public void test3() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("(1 + 2) * 3");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        int a = result.valueAtInt();
        assertEquals(result.valueAtInt(), 9);
    }

    @Test
    public void test4() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("(1 + 2) / 3");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        int a = result.valueAtInt();
        assertEquals(result.valueAtInt(), 1);
    }

    @Test
    public void test5() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("(1 + 2) / (3 - 1)");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        int a = result.valueAtInt();
        assertEquals(result.valueAtInt(), 1);
    }

    @Test
    public void test6() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("(1 + 2) % (3 - 1)");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        int a = result.valueAtInt();
        assertEquals(result.valueAtInt(), 1);
    }

    @Test
    public void test7() throws Exception {
        var parser = new Parser();

        var rootNode = parser.parse("'foo' + 'bar'");
        var visitor = new Visitor();
        var result = visitor.visit(rootNode);

        assertEquals(result.valueAtString(), "foobar");
    }
}
