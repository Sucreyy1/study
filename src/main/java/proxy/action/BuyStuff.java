package proxy.action;

import proxy.service.IBoss;
import proxy.service.impl.Boss;
import proxy.service.impl.ProxyBoss;

public class BuyStuff {
    public static void main(String[] args) throws Exception {
//        IBoss boss = new Boss();
//        int money = boss.yifu("12");
//        System.out.println("衣服成交:" + money);
        //调用代理方法
        IBoss boss = ProxyBoss.getProxyBoss(20);
        int yifu = boss.yifu("12");
        System.out.println(yifu);
    }
}
