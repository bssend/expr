package vashee.expr;

public enum TokenType {
    STRING("^\\'.+?\\'") ,
    INTEGER("^[0-9]+") ,
    DECIMAL("^[0-9]\\.[0-9]+") ,
    WHITESPACE("^\\s") ,
    TAB("^\\t") ,
    LPAREN("^\\(") ,
    RPAREN("^\\)") ,
    IDENTIFIER("^[a-zA-Z][a-zA-Z0-9]+") ,
    COMMA("^\\,") ,
    ADD_OP("^\\+") ,
    SUB_OP("^\\-") ,
    MUL_OP("^\\*") ,
    DIV_OP("^\\/") ,
    MOD_OP("^\\%") ;

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
