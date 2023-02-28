package Others;

public class ReflectTargetObject {
    private String value;

    public ReflectTargetObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private void privateMethod() {
        System.out.println("Call private method!");
    }
}