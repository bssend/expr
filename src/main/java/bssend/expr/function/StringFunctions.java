package bssend.expr.function;

import bssend.expr.annotation.Function;

public class StringFunctions {

    @Function("replace")
    public static String replace(String source, String target, String replacement) {
        return source.replace(target, replacement);
    }

    @Function("substring")
    public static String substring(String source, int beginIndex) {
        return source.substring(beginIndex);
    }

    @Function("substring")
    public static String substring(String source, int beginIndex, int endIndex) {
        return source.substring(beginIndex, endIndex);
    }

}
