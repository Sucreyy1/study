package proxy3;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB(code generate libary)
 * 字节码生成技术实现AOP，其实就是继承被代理对象，然后Override需要被代理的方法，
 * 在覆盖该方法时，自然是可以插入我们自己的代码的。因为需要Override被代理对象的方法，
 * 所以自然CGLIB技术实现AOP时，就必须要求需要被代理的方法不能是final方法，因为final方法不能被子类覆盖。
 */
public class CGProxy implements MethodInterceptor {

    //被代理的对象
    private Object target;

    public CGProxy(Object  target){
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("do sth before...");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("do sth after...");
        return result;
    }

    /*
    它的原理是生成一个父类enhancer.setSuperclass(this.target.getClass())的子类enhancer.create()
    然后对父类的方法进行拦截enhancer.setCallback(this),对父类的方法进行覆盖,所以父类的方法不能是final修饰的.
     */
    public Object getProxyObject(){
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(this.target.getClass());
        //设置回调
        enhancer.setCallback(this); //在调用父类方法的时,回调this.intercept()
        //创建代理对象
        return enhancer.create();

    }
}
