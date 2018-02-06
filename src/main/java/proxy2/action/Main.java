package proxy2.action;

import proxy2.service.IBoss;
import proxy2.service.impl.Boss;
import proxy2.service.impl.ProxyBoss;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        IBoss boss = new Boss();
        ProxyBoss proxyBoss = new ProxyBoss(boss);
        IBoss invocationHandler = (IBoss) Proxy.newProxyInstance(boss.getClass().getClassLoader(), boss.getClass().getInterfaces(), proxyBoss);
        int pants = invocationHandler.pants("12");
        System.out.println(pants);
    }
}
