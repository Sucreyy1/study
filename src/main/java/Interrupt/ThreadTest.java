package Interrupt;

import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class ThreadTest extends Thread {

    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    System.out.println("take()");
                    return queue.take();
                } catch (InterruptedException e) {
                    System.out.println("catch:" + Thread.currentThread().isInterrupted());
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
                System.out.println("finally:" + Thread.currentThread().isInterrupted());
            }
        }
    }

    public static void main(String[] args) {
//        Thread thread = new Thread(() -> new ThreadTest().getNextTask(new LinkedBlockingQueue<Task>()));
//        thread.start();
//        Thread thread1 = new Thread(()->{
//            try {
//                while (true){
//                    thread.interrupt();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        thread1.start();

        Thread thread = new Thread(()->{
            System.out.println("打游戏");
            System.out.println("before:"+Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println("出门");
            System.out.println("after:"+Thread.currentThread().isInterrupted());
            System.out.println(Thread.interrupted());
            System.out.println("interrupted:"+Thread.currentThread().isInterrupted());
        });
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.shutdownNow();
    }
}
