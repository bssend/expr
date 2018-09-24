package bssend.expr.node;

import bssend.expr.ValueType;

public interface IValueNode extends INode {

    ValueType getValueType();

    boolean isString();
    boolean isInteger();
    boolean isDouble();
    boolean isNumeric();
    boolean isBoolean();

    String stringValue();
    int intValue();
    double doubleValue();
    boolean booleanValue();
    Object value();
}
