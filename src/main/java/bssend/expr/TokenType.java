package bssend.expr;

public enum TokenType {
    STRING(0,"^'.+?'") ,
    DECIMAL(1, "^-?[0-9]+\\.[0-9]+") ,
    INTEGER(2,"^-?[0-9]+") ,
    BOOLEAN(3,"^(true|false)") ,

    WHITESPACE(4, "^\\s") ,
    TAB(5, "^\\t") ,
    LPAREN(6, "^\\(") ,
    RPAREN(7, "^\\)") ,
    IDENTIFIER(8, "^[a-zA-Z][a-zA-Z0-9]+") ,
    COMMA(9,"^,") ,

    ADD_OP(10,"^\\+") ,
    SUB_OP(11, "^\\-") ,
    MUL_OP(12, "^\\*") ,
    DIV_OP(13, "^\\/") ,
    MOD_OP(14, "^%") ,

    AND_OP(15, "^&&") ,
    OR_OP(16, "^\\|\\|") ,

    EQ_OP(17, "^==") ,
    NE_OP(18, "^!=") ,
    LTE_OP(19, "^<=") ,
    LT_OP(20, "^<") ,
    GTE_OP(21, "^>=") ,
    GT_OP(22, "^>") ;

    private final String pattern;
    private final int priority;

    TokenType(int priority, String pattern) {
        this.priority = priority;
        this.pattern = pattern;
    }

    public int getPriority() {
        return priority;
    }

    public String getPattern() {
        return pattern;
    }
}
