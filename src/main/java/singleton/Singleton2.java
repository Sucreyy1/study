package singleton;

/**
 * 单例模式-饿汉模式
 * 类加载时即创建对象,没有线程安全问题
 */
public class Singleton2 {

    private static final Singleton2 singleton2 = new Singleton2();

    private Singleton2(){

    }

    public static Singleton2 getInstance(){
        return singleton2;
    }
}
