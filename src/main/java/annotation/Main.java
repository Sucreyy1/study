package annotation;

public class Main {


    public static void main(String[] args) {
        Test test = new Test();
        MyProxy myProxy = new MyProxy(test);
        Test proxy = (Test)myProxy.getProxy();
        proxy.get();
    }
}
