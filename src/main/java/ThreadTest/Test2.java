package ThreadTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 主线程和子线程交替执行
 */
public class Test2 {


    public static void main(String[] args) {
        loop loop = new loop();
        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                loop.subRun(i);
            }
        }).start();
        for (int i = 0; i < 50; i++) {
            loop.mainRun(i);
        }
    }

    static class loop{
        private boolean mainRun = false;
        Lock lock = new ReentrantLock(true);
        Condition conditionSub = lock.newCondition();
        Condition conditionMain = lock.newCondition();

        public void subRun(int j){
            lock.lock();
            System.out.println("子线程 loop of "+j);
            while (!mainRun){
                try {
                    conditionSub.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                for (int i = 0; i < 50; i++) {
//                    System.out.println("sub run" + i);
                }
                mainRun = false;
                conditionMain.signal();
            } finally {
                lock.unlock();
            }
        }

        public void mainRun(int j){
            lock.lock();
            System.out.println("主线程 loop of "+j);
            while (mainRun){
                try {
                    conditionMain.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                for (int i = 0; i < 100; i++) {
//                    System.out.println("main run "+ i);
                }
                mainRun = true;
                conditionSub.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
