package ThreadTest;

import java.util.concurrent.Semaphore;

/**
 * 使用信号量Semaphore来控制执行流程
 */
public class Test3 {

    private static Semaphore semaphore1 = new Semaphore(1);
    private static Semaphore semaphore2 = new Semaphore(1);
    private static Semaphore semaphore3 = new Semaphore(1);

    public static void main(String[] args) {
        //保证A线程先执行
        try {
            semaphore2.acquire();//先申请许可,保证下面线程B执行acqiure时阻塞
            semaphore3.acquire();//先申请许可,保证下面线程C执行acquire时阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                print.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                print.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                print.printC();
            }
        }, "C").start();
    }

    static class Print {

        public void printA() {
                try {
                    semaphore1.acquire();//A线程获取许可,获取不到则阻塞
                    System.out.println(Thread.currentThread().getName());
                    semaphore2.release();//释放B线程的许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        public void printB() {
                try {
                    semaphore2.acquire();//B线程获取许可,获取不到则阻塞
                    System.out.println(Thread.currentThread().getName());
                    semaphore3.release();//释放C线程的许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        public void printC() {
                try {
                    semaphore3.acquire();//C线程获取许可,获取不到则阻塞
                    System.out.println(Thread.currentThread().getName());
                    semaphore1.release();//释放A线程的许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
