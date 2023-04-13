package Others.ProxyDemo;

import java.lang.reflect.Proxy;

public class JDKProxyFactory {
    public static Object getJDKProxy(Object target) {

        // 动态创建代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new JDKProxy(target));
    }
}
