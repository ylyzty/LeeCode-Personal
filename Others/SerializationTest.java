package Others;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationTest {
    public static void main(String[] args) {
        // Person person = new Person();
        // write(person);
        Person.a = 100;
        read("F:\\person.ser");
    }

    public static void write(Person person) {
        try {
            FileOutputStream fout = new FileOutputStream("F:\\person.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(person);
            oos.close();
        } catch (Exception e1) {
            e1.printStackTrace();
            // TODO: handle exception
        }
    }

    public static void read(String path) {
        try {
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Person person  = (Person) ois.readObject();
            ois.close();

            // 序列化之后
            System.out.println("========= 序列化后 ========");
            System.out.println(Person.serialVersionUID + "    " + person.age + "    " + person.a);
        } catch (Exception e2) {
            e2.printStackTrace();
            // TODO: handle exception
        }
    }
}


/**
 * static 修饰的变量不会序列化到字节流
 * 但是 serialVersionUID 除外
 * 
 */
class Person implements Serializable{
    public static final long serialVersionUID = 1L;
    public static final int age = 50;
    public static int a = 20;
    public int num = 30;
}
