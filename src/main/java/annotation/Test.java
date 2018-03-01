package annotation;

public class Test{

    public void put(){
        System.out.println("put method");
    }

    @Authorized({"aaa","bbb"})
    public void get(){
        System.out.println("get method");
    }


}
