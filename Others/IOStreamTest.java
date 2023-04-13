package Others;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import Others.Person;

/**
 * InputStream: 字节输入流
 * OutputStream: 字节输出流
 * Reader: 字符输入流, 对字节流读取时, 查找指定的码表
 * Writer: 字符输出流
 */
public class IOStreamTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 字节输出流
        FileOutputStream fos = new FileOutputStream("./TestFiles/output.txt");
        byte[] arr = "This is an output stream test!".getBytes();
        fos.write(arr);
        fos.close();

        // 序列化
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./TestFiles/object.data"));
        Person person = new Person("null", 0);
        oos.writeObject(person);
        oos.close();


        // 字节输入流
        FileInputStream fis = new FileInputStream("./TestFiles/input.txt");
        int content;
        while ((content = fis.read()) != - 1) {
            System.out.print((char) content);
        }
        fis.close();

        // 读取序列化的数据, 反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./TestFiles/object.data"));
        Person readPerson = (Person) ois.readObject();
        System.out.println(readPerson.toString());
        ois.close();


        // 字符流, 默认采用 Unicode 编码
        // InputStreamReader 字节流转字符流
        // FileReader extends InputStreamReader
        FileReader fileReader = new FileReader("./TestFiles/input.txt");
        while ((content = fileReader.read()) != -1) {
            System.out.print((char) content);
        }
        fileReader.close();

        FileWriter fileWriter = new FileWriter("/TestFiles/output.txt");
        fileWriter.write("This is file writer test!");
        fileWriter.close();


        // 字节缓冲流
        // IO 操作是很消耗性能的，缓冲流将数据加载至缓冲区，一次性读取/写入多个字节，从而避免频繁的 IO 操作，提高流的传输效率
        // 采用了装饰器模式来增强 InputStream 和OutputStream子类对象的功能
        // private static int DEFAULT_BUFFER_SIZE = 8192; 8kb
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        // 本质是调用 read(byte[], int offset, int length)
        // 存储在内部 byte[] buf 中
        bis.read();

        // 先写入缓冲区，写满后刷入磁盘，缓冲区大小8192字节，8kb
        // 调用 write(byte[], int offset, int length)
        bos.write("Alice is 9 years old now!".getBytes());
        bis.close();
        bos.close();


        // 字节打印流
        // 随机访问流 RandomAccessFile  大文件断点续传
    }
}
