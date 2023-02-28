package Others;

public class ClassLoaderTest extends ClassLoader{

    private String path;

    public ClassLoaderTest(String path) {
        this.path = path;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        Class c = null;
        
        // 自定义获取 class 文件的字节码数组
        byte[] classData = getClassData();
        if (classData != null) {
            // 创建 class
            c = defineClass(name, classData, 0, classData.length);
        }

        return c;
    }

    private byte[] getClassData() {
        return null;
    }
}
