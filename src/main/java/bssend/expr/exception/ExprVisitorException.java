package bssend.expr.exception;

public class ExprVisitorException extends RuntimeException{
    public ExprVisitorException(String message) {
        super(message);
    }

    public ExprVisitorException(String message, Throwable t) {
        super(message, t);
    }

    public ExprVisitorException(Throwable t) {
        super(t);
    }

}
