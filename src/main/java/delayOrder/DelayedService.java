package delayOrder;


import java.util.Scanner;
import java.util.concurrent.DelayQueue;

public class DelayedService {

    private boolean start;

    private static DelayQueue<BaseDelayed<?>> delayQueue = new DelayQueue<>();

    public static void main(String[] args) {
        DelayedService delayedService = new DelayedService();
        new Thread(() -> {
            System.out.println("生产者已启动");
            delayedService.producer();
        }).start();
        new Thread(() -> {
            System.out.println("消费者已启动");
            try {
                delayedService.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要支付的订单id");
        int i = sc.nextInt();
        delayQueue.forEach(item ->{
            if (item.getId() == i){
                delayQueue.remove(item);
            }
        });
    }

    public void producer() {
        for (int i = 0; i < 4; i++) {
            BaseDelayed<String> order = new BaseDelayed<>(i, (i + 1) * 10, "Sucre" + i);
            System.out.println("客户：" + order.getValue());
            delayQueue.put(order);
        }
    }

    public void consumer() throws InterruptedException {
        while (true) {
            BaseDelayed<?> order = delayQueue.take();
            System.out.println("订单：" + order.getValue() + "已超时");
        }
    }

}
