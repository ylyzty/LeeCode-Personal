package Others.CglibProxy;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyFactory {
    public static Object getCglibProxy(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxy());

        return enhancer.create();
    }
}
