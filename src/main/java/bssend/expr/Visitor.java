package bssend.expr;

import bssend.expr.exception.ExprCompileException;
import bssend.expr.exception.ExprVisitorException;
import bssend.expr.function.FunctionFactory;
import lombok.NonNull;
import lombok.var;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Visitor implements IVisitor{

    @Override
    public IValueNode visit(final INode node) {

        switch (node.getNodeType()) {
            case Value:
                return visit((IValueNode)node);

            case BinaryExpr:
                return visit((IBinaryExprNode)node);

            case FunctionCall:
                return visit((IFunctionCallNode)node);

            default:
                break;
        }

        throw new ExprVisitorException("Undefined node type");
    }

    @Override
    public IValueNode visit(final IValueNode node) {
        return node;
    }

    @Override
    public final IValueNode visit(@NonNull final IFunctionCallNode node) {

        var funcNode = (IFunctionCallNode)node;

            List<Class<?>> argumentTypes = funcNode.getArguments().stream()
                    .map(n -> visit(n).getValueType().getClazz())
                    .collect(Collectors.toList());

            var arguments = funcNode.getArguments().stream()
                    .map(n -> visit(n).value())
                    .collect(Collectors.toList());

            var invokee = (Method) FunctionFactory
                    .create(funcNode.getFunctionName(), argumentTypes);

        try {
            return ValueNode.of(invokee.invoke(null, arguments.toArray()));
        } catch (Exception t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public final IValueNode visit(@NonNull final IBinaryExprNode node) {

        switch (node.getOperator()) {
            case Add:
                return visitAdd(node);
            case Mul:
                return visitMul(node);
            case Sub:
                return visitSub(node);
            case Div:
                return visitDiv(node);
            case Mod:
                return visitMod(node);
            case Equal:
                return visitEq(node);
            case NotEqual:
                return visitNe(node);
            case LessThan:
                return visitLt(node);
            case LessThanEqual:
                return visitLte(node);
            case GreaterThan:
                return visitGt(node);
            case GreaterThanEqual:
                return visitGte(node);
            case And:
                return visitAnd(node);
            case Or:
                return visitOr(node);
        }

        throw new ExprVisitorException("Undefined operator.");
    }

    /**
     * <pre>
     * Visit a binary expression node of + operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitAdd(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        if (!Arrays.asList(
                ValueType.String, ValueType.Integer, ValueType.Double)
                    .contains(left.getValueType()))
            throw new ExprVisitorException(
                    "Add operator does not supported value type. (" + left.getValueType() + ")");

        if (!Arrays.asList(
                ValueType.String, ValueType.Integer, ValueType.Double)
                    .contains(right.getValueType()))
            throw new ExprVisitorException(
                    "Add operator does not supported value type. (" + right.getValueType() + ")");

            // string + operation
        if (left.isString() && right.isString())
            return ValueNode.of(
                    left.stringValue() + right.stringValue());

        // numeric + operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() + right.doubleValue());

        throw new ExprVisitorException(
                "Can not operation value type. (" +
                        left.getValueType() + "," +
                        right.getValueType() + ")");
    }

    /**
     * <pre>
     * Visit a binary expression node of - operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitSub(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric - operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() - right.doubleValue());

        throw new RuntimeException("could not - operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of * operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitMul(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() * right.doubleValue());

        throw new RuntimeException("could not * operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of / operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitDiv(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() / right.doubleValue());

        throw new RuntimeException("could not / operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of % operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitMod(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                left.doubleValue() % right.doubleValue());

        throw new RuntimeException("could not % operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of == operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitEq(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric == operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                left.doubleValue() == right.doubleValue());

        // string == operation
        if (left.isString() && right.isString())
            return ValueNode.of(
                    left.stringValue().equals(right.stringValue()));

        // boolean == operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
                left.booleanValue() == right.booleanValue());

        throw new RuntimeException("could not == operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of != operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitNe(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric != operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() != right.doubleValue());

        // string != operation
        if (left.isString() && right.isString())
            return ValueNode.of(
                    !left.stringValue().equals(right.stringValue()));

        // boolean != operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
            left.booleanValue() != right.booleanValue());

        throw new RuntimeException("could not != operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of < operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitLt(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric < operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() < right.doubleValue());

        throw new RuntimeException("could not < operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of <= operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitLte(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric <= operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() <= right.doubleValue());

        throw new RuntimeException("could not <= operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of > operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitGt(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric > operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() > right.doubleValue());

        throw new RuntimeException("could not > operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of >= operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitGte(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric >= operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                left.doubleValue() >= right.doubleValue());

        throw new RuntimeException("could not >= operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of && operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitAnd(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // boolean && operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
                left.booleanValue() && right.booleanValue());

        throw new RuntimeException("could not && operation");
    }

    /**
     * <pre>
     * Visit a binary expression node of || operator.
     * </pre>
     * @param node
     * @return
     */
    private final IValueNode visitOr(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // boolean && operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
                left.booleanValue() || right.booleanValue());

        throw new RuntimeException("could not || operation");
    }

}
