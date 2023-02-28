package Others.ProxyDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JDKProxyInvocationHandler implements InvocationHandler{

    private final Object target;

    public JDKProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("Before: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("After: " + method.getName());
        return result;
    }
    
}
