package annotation;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyProxy implements MethodInterceptor {

    private Object target;

    public MyProxy(Object target){
        this.target = target;
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(method.getAnnotation(Authorized.class)!=null){
            Method[] declaredMethods = method.getAnnotation(Authorized.class).annotationType().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                System.out.println(declaredMethod.getName());
                String[] invoke = (String[])declaredMethod.invoke(method.getAnnotation(Authorized.class));
                for (String s : invoke) {
                    System.out.println(s);
                }
            }
            System.out.println(method.getName()+" before");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println(method.getName()+" after");
            return result;
        }
        return methodProxy.invokeSuper(o, objects);
    }

    public Object getProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
