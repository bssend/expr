package bssend.expr;

import bssend.expr.exception.ExprVisitorException;
import bssend.expr.function.FunctionFactory;
import bssend.expr.node.*;
import lombok.NonNull;
import lombok.val;
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

        val funcNode = (IFunctionCallNode)node;

        List<Class<?>> argumentTypes
                = funcNode.getArguments().stream()
                    .map(n -> visit(n).getValueType().getClazz())
                    .collect(Collectors.toList());

        var arguments
                = funcNode.getArguments().stream()
                    .map(n -> visit(n).value())
                    .collect(Collectors.toList());

        var function
                = (Method) FunctionFactory
                    .create(funcNode.getFunctionName(), argumentTypes);

        try {
            return ValueNode.of(function.invoke(null, arguments.toArray()));
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
    private IValueNode visitAdd(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

            // string + operation
        if (left.isString() && right.isString())
            return ValueNode.of(
                    left.stringValue() + right.stringValue());

        // integer + operation
        if (left.isInteger() && right.isInteger())
            return ValueNode.of(
                    left.intValue() + right.intValue());

        // numeric + operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() + right.doubleValue());

        throw new ExprVisitorException(
                String.format("ADD operator does not support value type. (%s + %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of - operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitSub(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // integer - operation
        if (left.isInteger() && right.isInteger())
            return ValueNode.of(
                    left.intValue() - right.intValue());

        // numeric - operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() - right.doubleValue());

        throw new ExprVisitorException(
                String.format("SUB operator does not support value type. (%s - %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of * operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitMul(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // integer * operation
        if (left.isInteger() && right.isInteger())
            return ValueNode.of(
                    left.intValue() * right.intValue());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() * right.doubleValue());

        throw new ExprVisitorException(
                String.format("MUL operator does not support value type. (%s * %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of / operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitDiv(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                    left.doubleValue() / right.doubleValue());

        throw new ExprVisitorException(
                String.format("DIV operator does not support value type. (%s / %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of % operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitMod(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric * operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                left.doubleValue() % right.doubleValue());

        throw new ExprVisitorException(
                String.format("MOD operator does not support value type. (%s %% %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of == operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitEq(final IBinaryExprNode node) {

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

        throw new ExprVisitorException(
                String.format("EQ operator does not support value type. (%s == %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of != operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitNe(final IBinaryExprNode node) {

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

        throw new ExprVisitorException(
                String.format("NE operator does not support value type. (%s != %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of < operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitLt(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric < operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() < right.doubleValue());

        throw new ExprVisitorException(
                String.format("LT operator does not support value type. (%s < %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of <= operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitLte(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric <= operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() <= right.doubleValue());

        throw new ExprVisitorException(
                String.format("LTE operator does not support value type. (%s <= %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of > operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitGt(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric > operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
            left.doubleValue() > right.doubleValue());

        throw new ExprVisitorException(
                String.format("GT operator does not support value type. (%s > %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of >= operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitGte(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // numeric >= operation
        if (left.isNumeric() && right.isNumeric())
            return ValueNode.of(
                left.doubleValue() >= right.doubleValue());

        throw new ExprVisitorException(
                String.format("GTE operator does not support value type. (%s >= %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of && operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitAnd(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // boolean && operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
                left.booleanValue() && right.booleanValue());

        throw new ExprVisitorException(
                String.format("AND operator does not support value type. (%s && %s)",
                        left.getValueType(), right.getValueType()));
    }

    /**
     * <pre>
     * Visit a binary expression node of || operator.
     * </pre>
     * @param node
     * @return
     */
    private IValueNode visitOr(final IBinaryExprNode node) {

        var left = visit(node.getLeftNode());
        var right = visit(node.getRightNode());

        // boolean && operation
        if (left.isBoolean() && right.isBoolean())
            return ValueNode.of(
                left.booleanValue() || right.booleanValue());

        throw new ExprVisitorException(
                String.format("OR operator does not support value type. (%s || %s)",
                        left.getValueType(), right.getValueType()));
    }

}
