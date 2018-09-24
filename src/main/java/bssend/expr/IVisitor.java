package bssend.expr;

import bssend.expr.node.IBinaryExprNode;
import bssend.expr.node.IFunctionCallNode;
import bssend.expr.node.INode;
import bssend.expr.node.IValueNode;

public interface IVisitor {
    IValueNode visit(final INode node);
    IValueNode visit(final IValueNode node);
    IValueNode visit(final IFunctionCallNode node);
    IValueNode visit(final IBinaryExprNode node);
}
