package vashee.expr;

public interface ITokenSequence {
    Token next();
    Token peek();
    boolean isEnd();
}
