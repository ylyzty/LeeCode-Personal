package Others.ProxyDemo;

import java.lang.reflect.Proxy;

public class JDKProxyFactory {
    public static Object getJDKProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new JDKProxyInvocationHandler(target));
    }
}
