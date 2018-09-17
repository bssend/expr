package vashee.expr;

public interface IValueNode extends INode {
    Object getValue();

    boolean canValueOfInt();
    boolean canValueOfDouble();
    boolean canValueOfString();

    String valueAtString();
    int valueAtInt();
    double valueAtDouble();
}
