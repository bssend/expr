package bssend.expr;

import lombok.var;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void test1() throws Exception {
        var result = Expression.eval("1 + 2 + 3");
        assertEquals(result.intValue(), 6);
    }

    @Test
    public void test2() throws Exception {
        var result = Expression.eval("1 + 2 * 3");
        assertEquals(result.intValue(), 7);
    }

    @Test
    public void test3() throws Exception {
        var result = Expression.eval("(1 + 2) * 3");
        assertEquals(result.intValue(), 9);
    }

    @Test
    public void test4() throws Exception {
        var result = Expression.eval("(1 + 2) / 3");
        assertEquals(result.intValue(), 1);
    }

    @Test
    public void test5() throws Exception {
        var result = Expression.eval("(1 + 2) / (3 - 1)");
        assertEquals(result.intValue(), 1);
    }

    @Test
    public void test6() throws Exception {
        var result = Expression.eval("(1 + 2) % (3 - 1)");
        assertEquals(result.intValue(), 1);
    }

    @Test
    public void test7() throws Exception {
        var result = Expression.eval("'foo' + 'bar'");
        assertEquals(result.stringValue(), "foobar");
    }

    @Test
    public void test8() throws Exception {
        var result = Expression.eval("1 + 2 * (3 - 1 * (1 + 1))");
        assertEquals(result.intValue(), 3);
    }

    @Test
    public void test9() throws Exception {
        var result = Expression.eval("-1 + 2 * (3 - 1 * (1 + 1))");
        assertEquals(result.intValue(), 1);
    }

    @Test
    public void test10() throws Exception {
        var result = Expression.eval("-10.7 + 2.1");
        assertEquals(result.doubleValue(), -8.6);
    }

    @Test
    public void test11() throws Exception {
        var result = Expression.eval("trunc(-10.7) + 2.1");
        assertEquals(result.doubleValue(), -7.9);
    }

    @Test
    public void test12() throws Exception {
        var result = Expression.eval("substring('foobar', 1, 4)");
        assertEquals(result.stringValue(), "oob");
    }

    @Test
    public void test14() throws Exception {
        var result = Expression.eval("substring('foobar', 4)");
        assertEquals(result.stringValue(), "ar");
    }

    @Test
    public void test15() throws Exception {
        var result = Expression.eval("replace('foobar', 'ob', 'ok')");
        assertEquals(result.stringValue(), "fookar");
    }

    @Test
    public void test16() throws Exception {
        var result = Expression.eval("4 == 4");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test17() throws Exception {
        var result = Expression.eval("4 == 5");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test18() throws Exception {
        var result = Expression.eval("4 != 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test19() throws Exception {
        var result = Expression.eval("4 != 5");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test20() throws Exception {
        var result = Expression.eval("4 < 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test21() throws Exception {
        var result = Expression.eval("4 < 5");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test22() throws Exception {
        var result = Expression.eval("4 <= 3");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test23() throws Exception {
        var result = Expression.eval("4 <= 4");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test24() throws Exception {
        var result = Expression.eval("4 > 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test25() throws Exception {
        var result = Expression.eval("5 > 4");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test26() throws Exception {
        var result = Expression.eval("3 >= 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test27() throws Exception {
        var result = Expression.eval("4 >= 4");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test28() throws Exception {
        var result = Expression.eval("3 < 4 && 3 <= 3");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test29() throws Exception {
        var result = Expression.eval("4 <= 4 && 4 < 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void test30() throws Exception {
        var result = Expression.eval("3 > 4 || 3 >= 3");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void test31() throws Exception {
        var result = Expression.eval("3 >= 4 || 4 > 4");
        assertEquals(result.booleanValue(), false);
    }

    @Test
    public void complex1() throws Exception {
        var result = Expression.eval("trunc(13.4) >= 13");
        assertEquals(result.booleanValue(), true);
    }

    @Test
    public void complex2() throws Exception {
        var result = Expression.eval("replace('foobar', 'ob', 'ok') + 'baz'");
        assertEquals(result.stringValue(), "fookarbaz");
    }
}
