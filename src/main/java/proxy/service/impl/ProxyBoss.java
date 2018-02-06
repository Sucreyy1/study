package proxy.service.impl;

import proxy.service.IBoss;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBoss {

    public static IBoss getProxyBoss(final int coupon) throws Exception {
        Object proxyedObj = Proxy.newProxyInstance(Boss.class.getClassLoader(), Boss.class.getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Integer returnValue = (Integer) method.invoke(new Boss(), args);
                return returnValue - coupon;
            }
        });
        return (IBoss) proxyedObj;
    }
}
