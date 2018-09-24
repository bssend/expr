package bssend.expr.node;

import bssend.expr.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class BinaryExprNode extends Node implements IBinaryExprNode {

    private final INode leftNode;
    private final INode rightNode;
    private final BinaryOperatorType operator;

    public BinaryExprNode(
            @NonNull final INode leftNode,
            @NonNull final INode rightNode,
            BinaryOperatorType operator) {
        super(NodeType.BinaryExpr);
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }
}
