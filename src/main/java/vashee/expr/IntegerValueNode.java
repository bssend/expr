package vashee.expr;

public class IntegerValueNode implements IValueNode {

    private final Integer value;

    public IntegerValueNode(final Integer value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean canValueOfInt() {
        return true;
    }

    @Override
    public boolean canValueOfDouble() {
        return true;
    }

    @Override
    public boolean canValueOfString() {
        return false;
    }

    @Override
    public String valueAtString() {
        throw new RuntimeException("cant cast string value.");
    }

    @Override
    public int valueAtInt() {
        return value;
    }

    @Override
    public double valueAtDouble() {
        return value.doubleValue();
    }
}
