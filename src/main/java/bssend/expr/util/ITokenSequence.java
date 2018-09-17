package bssend.expr.util;

import bssend.expr.Token;

public interface ITokenSequence {
    Token next();
    Token peek();
    boolean isEnd();
}
