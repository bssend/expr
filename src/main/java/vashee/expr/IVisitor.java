package vashee.expr;

public interface IVisitor {
    IValueNode visit(INode node);
}
