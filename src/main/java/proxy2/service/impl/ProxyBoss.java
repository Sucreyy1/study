package proxy2.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyBoss implements InvocationHandler {

    private Object target;

    public ProxyBoss() {

    }

    public ProxyBoss(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("pants".equals(method.getName())){
//            Integer money = (Integer) method.invoke(target, args);
//            System.out.println("裤子"+money);
            return 1000;
        }else {
            Integer result = (Integer) method.invoke(target, args);
            return result;
        }
    }
}
