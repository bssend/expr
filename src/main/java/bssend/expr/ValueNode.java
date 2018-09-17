package bssend.expr;

import lombok.NonNull;

import java.math.BigDecimal;

public class ValueNode extends Node implements IValueNode {

    private final ValueType valueType;

    private final Object value;

//    private String stringValue;
//    private Integer intValue;
//    private Double doubleValue;
//    private Boolean booleanValue;

    private ValueNode(@NonNull final String value) {
        super(NodeType.Value);
        this.valueType = ValueType.String;
        this.value = value;
        //this.stringValue = value;
    }

    private ValueNode(@NonNull final Integer value) {
        super(NodeType.Value);
        this.valueType = ValueType.Integer;
        //this.intValue = value;
        this.value = value;
    }

    private ValueNode(@NonNull final Double value) {
        super(NodeType.Value);
        this.valueType = ValueType.Double;
//        this.doubleValue = value;
        this.value = value;
    }

    private ValueNode(@NonNull final Boolean value) {
        super(NodeType.Value);
        this.valueType = ValueType.Boolean;
//        this.booleanValue = value;
        this.value = value;
    }

    public static IValueNode of(@NonNull final String value) {
        return new ValueNode(value);
    }

    public static IValueNode of(@NonNull final Integer value) {
        return new ValueNode(value);
    }

    public static IValueNode of(@NonNull final Double value) {
        return new ValueNode(value);
    }

    public static IValueNode of(@NonNull final Boolean value) {
        return new ValueNode(value);
    }

    public static IValueNode of(@NonNull final Object value) {

        if (value instanceof String)
            return new ValueNode((String)value);

        if (value instanceof Integer)
            return new ValueNode((Integer)value);

        if (value instanceof Double)
            return new ValueNode((Double)value);

        if (value instanceof Boolean)
            return new ValueNode((Boolean)value);

        throw new RuntimeException("unsupported value type.");
    }

    @Override
    public ValueType getValueType() {
        return valueType;
    }

    @Override
    public boolean isString() {
        return valueType == ValueType.String;
    }

    @Override
    public boolean isInteger() {
        return valueType == ValueType.Integer;
    }

    @Override
    public boolean isDouble() {
        return valueType == ValueType.Double;
    }

    @Override
    public boolean isNumeric() {
        return valueType == ValueType.Integer ||
                valueType == ValueType.Double;
    }

    @Override
    public boolean isBoolean() {
        return valueType == ValueType.Boolean;
    }

    @Override
    public String stringValue() {
        if (!isString())
            throw new RuntimeException("value is not string.");
        return (String)value;
    }

    @Override
    public int intValue() {

        if (isInteger())
            return (Integer)value;
//            return intValue;

        if (isDouble())
            return ((Double)value).intValue();
//            return doubleValue.intValue();

        throw new RuntimeException("value is not numeric.");
    }

    @Override
    public double doubleValue() {

        if (isInteger())
            return Double.valueOf((Integer)value);
            //return intValue.doubleValue();

        if (isDouble())
            return (Double)value;
//            return doubleValue;

        throw new RuntimeException("value is not numeric.");
    }

    @Override
    public boolean booleanValue() {
        if (!isBoolean())
            throw new RuntimeException("value is not boolean.");
//        return booleanValue;
        return (Boolean)value;
    }

    @Override
    public Object value() {
        return value;
//        if (isString())
//            return stringValue();
//
//        if (isInteger())
//            return intValue();
//
//        if (isDouble())
//            return doubleValue();
//
//        if (isBoolean())
//            return booleanValue();
//
//        throw new RuntimeException("unsupported value type.");
    }
}
