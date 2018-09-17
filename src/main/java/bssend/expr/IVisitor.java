package bssend.expr;

public interface IVisitor {
    IValueNode visit(final INode node);
    IValueNode visit(final IValueNode node);
    IValueNode visit(final IFunctionCallNode node);
    IValueNode visit(final IBinaryExprNode node);
}
