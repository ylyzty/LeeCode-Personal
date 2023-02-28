package Others;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射使用操作
 */
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> targetClass = Class.forName("Others.ReflectTargetObject");
        
        // class.newInstance() 只能调用无参构造函数
        // ReflectTargetObject TargetObject = (ReflectTargetObject) targetClass.newInstance();

        Constructor<?> constructor = targetClass.getDeclaredConstructor(String.class);
        ReflectTargetObject targetObject = (ReflectTargetObject) constructor.newInstance("value");

        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        Method getValueMethod = targetClass.getDeclaredMethod("getValue");
        System.out.println(getValueMethod.invoke(targetObject));

        Method setValueMethod = targetClass.getDeclaredMethod("setValue", String.class);
        setValueMethod.invoke(targetObject, "newValue");
        System.out.println(targetObject.getValue());

        // 调用私有方法和私有属性, 需要取消安全检查, 否则抛出异常
        Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(targetObject);
    }
}
