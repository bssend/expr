package vashee.expr;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class BinaryExprNode implements INode {

    private final INode leftNode;
    private final INode rightNode;
    private final BinaryOperatorType operator;

    public BinaryExprNode(
            @NonNull final INode leftNode,
            @NonNull final INode rightNode,
            BinaryOperatorType operator) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.operator = operator;
    }
}
