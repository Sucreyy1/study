package ThreadTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程依次输出它的名字,要求必须以ABCABCABC...形式
 */
public class Test{

    public static void main(String[] args) {
        Print print = new Print();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                print.printA();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                print.printB();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                print.printC();
            }
        },"C").start();
    }

    static class Print{

        private String flag = "A";

        Lock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        public void printA(){
            lock.lock();
            while (!"A".equals(flag)){
                try {
                    conditionA.await();//阻塞A线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println(Thread.currentThread().getName());
                flag= "B";
                conditionB.signal();//唤醒B线程
            } finally {
                lock.unlock();
            }
        }
        public void printB(){
            lock.lock();
            while (!"B".equals(flag)){
                try {
                    conditionB.await();//阻塞B线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println(Thread.currentThread().getName());
                flag = "C";
                conditionC.signal();//唤醒C线程
            } finally {
                lock.unlock();
            }
        }
        public void printC(){
            lock.lock();
            while (!"C".equals(flag)){
                try {
                    conditionC.await();//阻塞C线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println(Thread.currentThread().getName());
                flag = "A";
                conditionA.signal();//唤醒A线程
            } finally {
                lock.unlock();
            }
        }
    }
}
