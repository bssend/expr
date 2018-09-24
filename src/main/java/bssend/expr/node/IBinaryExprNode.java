package bssend.expr.node;

import bssend.expr.BinaryOperatorType;

public interface IBinaryExprNode extends INode {
    INode getLeftNode();
    INode getRightNode();
    BinaryOperatorType getOperator();
}
