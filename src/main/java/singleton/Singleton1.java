package singleton;

/**
 * 单例模式-饱汉模式
 * 需要时才创建对象,加上同步避免线程安全问题
 */
public class Singleton1 {

    private static volatile Singleton1 singleton1;

    private Singleton1(){

    }

    public static Singleton1 getInstance(){
        if(null == singleton1){
            synchronized (Singleton1.class){
                if(null == singleton1){
                    singleton1 = new Singleton1();
                }
            }
        }
        return singleton1;
    }

}
