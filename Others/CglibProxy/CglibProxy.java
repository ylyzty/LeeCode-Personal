package Others.CglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    /**
     * @param obj 动态生成的代理对象
     * @param method 实际调用的方法
     * @param args 实际调用方法的入参
     * @param methodProxy Method代理方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = null;

        if (method.getName().equals("print")) {
            System.out.println("Before: " + method.getName());
            // 执行被代理类的逻辑
            result = methodProxy.invokeSuper(obj, args);
            System.out.println("After: " + method.getName());
        }
        else {
            result = methodProxy.invokeSuper(obj, args);
        }

        return  result;
    }
}
