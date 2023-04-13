package Others.ProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKProxy implements InvocationHandler{

    private final Object target;    // 被代理对象

    public JDKProxy(Object target) {
        this.target = target;
    }

    /**
     * 当调用被代理对象的方法时, 会自动跳转到代理对象的invoke()方法
     * 可以通过 method.getName() 指定被代理的方法
     * 默认会代理所有方法
     *
     * @param proxy 动态生成的代理对象
     * @param method 实际调用的方法
     * @param args 实际调用方法的入参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        Object result = null;
        if (method.getName().equals("send")) {
            System.out.println("Before: " + method.getName());
            result = method.invoke(target, args);
            System.out.println("After: " + method.getName());
            
        }
        
        else {
            result = method.invoke(target, args);
        }
        
        return result;
    } 
}
