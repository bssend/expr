package bssend.expr;

public enum ValueType {

    String(String.class) ,

    Integer(int.class) ,

    Double(double.class) ,

    Boolean(boolean.class);

    private Class<?> clazz;

    ValueType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
