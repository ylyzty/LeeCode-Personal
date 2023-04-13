package Others;

import java.io.Serializable;

public class Person implements Serializable{

    private static final long serialVersionUID = 2L;
    private static final int FLAG = 100;

    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static int getFlag() {
        return FLAG;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", FLAG=" + FLAG + "]";
    }
}
