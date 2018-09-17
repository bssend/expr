package bssend.expr;

public interface IBinaryExprNode extends INode {
    INode getLeftNode();
    INode getRightNode();
    BinaryOperatorType getOperator();
}
