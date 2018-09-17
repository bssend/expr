package vashee.expr;

public class StringValueNode implements IValueNode {

    private final String value;

    public StringValueNode(final String value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public boolean canValueOfInt() {
        return false;
    }

    @Override
    public boolean canValueOfDouble() {
        return false;
    }

    @Override
    public boolean canValueOfString() {
        return true;
    }

    @Override
    public String valueAtString() {
        return value;
    }

    @Override
    public int valueAtInt() {
        throw new RuntimeException("cant cast int value.");
    }

    @Override
    public double valueAtDouble() {
        throw new RuntimeException("cant cast double value.");
    }
}
