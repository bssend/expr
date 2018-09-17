package vashee.expr;

import lombok.var;

import static vashee.expr.BinaryOperatorType.MUL;

public class Visitor implements IVisitor{
    @Override
    public IValueNode visit(INode node) {
        if (node instanceof IValueNode)
            return (IValueNode) node;
        if (node instanceof BinaryExprNode)
            return visitBinaryExpr((BinaryExprNode) node);
        throw new RuntimeException("undefined node.");
    }

    private final IValueNode visitBinaryExpr(BinaryExprNode node) {

        var leftNode = visit(node.getLeftNode());
        var rightNode = visit(node.getRightNode());

        switch (node.getOperator()) {
            case ADD:
                if (leftNode.canValueOfString() || rightNode.canValueOfString())
                    return new StringValueNode(leftNode.valueAtString() + rightNode.valueAtString());

                if (leftNode.canValueOfInt() || rightNode.canValueOfInt())
                   return new IntegerValueNode(leftNode.valueAtInt() + rightNode.valueAtInt());

                if (leftNode.canValueOfDouble() || rightNode.canValueOfDouble())
                    return new DecimalValueNode(leftNode.valueAtDouble() + rightNode.valueAtDouble());

                throw new RuntimeException("not add operation");

            case MUL:
                if (leftNode.canValueOfInt() || rightNode.canValueOfInt())
                    return new IntegerValueNode(leftNode.valueAtInt() * rightNode.valueAtInt());

                if (leftNode.canValueOfDouble() || rightNode.canValueOfDouble())
                    return new DecimalValueNode(leftNode.valueAtDouble() * rightNode.valueAtDouble());

                throw new RuntimeException("not mul operation");

            case SUB:
                if (leftNode.canValueOfInt() || rightNode.canValueOfInt())
                    return new IntegerValueNode(leftNode.valueAtInt() - rightNode.valueAtInt());

                if (leftNode.canValueOfDouble() || rightNode.canValueOfDouble())
                    return new DecimalValueNode(leftNode.valueAtDouble() - rightNode.valueAtDouble());

                throw new RuntimeException("not sub operation");

            case DIV:
                if (leftNode.canValueOfInt() || rightNode.canValueOfInt())
                    return new IntegerValueNode(leftNode.valueAtInt() / rightNode.valueAtInt());

                if (leftNode.canValueOfDouble() || rightNode.canValueOfDouble())
                    return new DecimalValueNode(leftNode.valueAtDouble() / rightNode.valueAtDouble());

                throw new RuntimeException("not sub operation");

            case MOD:
                if (leftNode.canValueOfInt() || rightNode.canValueOfInt())
                    return new IntegerValueNode(leftNode.valueAtInt() % rightNode.valueAtInt());

                if (leftNode.canValueOfDouble() || rightNode.canValueOfDouble())
                    return new DecimalValueNode(leftNode.valueAtDouble() % rightNode.valueAtDouble());

                throw new RuntimeException("not mod operation");
        }

        throw new RuntimeException("undefined operator.");
    }
}
