package bssend.expr.exception;

import bssend.expr.Expression;

public class ExprCompileException extends RuntimeException {

    public ExprCompileException(String message) {
        super(message);
    }

    public ExprCompileException(String message, Throwable t) {
        super(message, t);
    }

    public ExprCompileException(Throwable t) {
        super(t);
    }
}
