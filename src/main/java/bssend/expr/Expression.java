package bssend.expr;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import lombok.var;

@Getter
public class Expression {

    private static final IParser PARSER = new Parser();
    private static final IVisitor VISITOR = new Visitor();

    private final INode rootNode;

    private Expression(@NonNull final INode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * <pre>
     * Compile a expression string.
     * </pre>
     * @param s
     * @return
     */
    public static Expression compile(@NonNull final String s) {
        return new Expression(PARSER.parse(s));
    }

    /**
     * <pre>
     * Evaluate a string as an expression.
     * </pre>
     * @param s
     * @return
     */
    public static IValueNode eval(@NonNull final String s) {
        return compile(s).eval();
    }

    public IValueNode eval() {
        return VISITOR.visit(rootNode);
    }

}
