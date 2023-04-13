package Others;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassLoaderTest extends ClassLoader{

    /**
     * 类加载路径
     */
    private String path;

    public ClassLoaderTest(String path) {
        this.path = path;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(path, fileName);

        try {
            // 获取 class 文件, 读取到内存中
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int length = 0;
            try {
                while ((length = fis.read()) != -1) {
                    baos.write(length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] data = baos.toByteArray();
            fis.close();
            baos.close();

            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 加载不了则 ClassLoader.findClass方法, 抛出异常
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        }
        else {
            return name.substring(index + 1) + ".class";
        }
    }
}
