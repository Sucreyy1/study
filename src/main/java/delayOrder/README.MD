DelayQueue
----------
* 基本概念
    1. DelayQueue
        >DelayQueue是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，
        其中的对象只能在其盗取时才能从队列中取出来。这种队列是有序的，即队头对
        象的延迟到期时间最长。  
        **注意：不能将null元素放入队列。**
    2. Delayed
        >一种混合风格的接口，用来标记哪些应该在给定延迟时间之后执行的对象。此接
        口的实现必须定义一个compareTo方法，该方法提供与此接口的getDelay方法一
        致的排序。
    
* 基本用法  

        //其中DelayedObject必须为实现了Delayed接口的类
        DelayQueue<DelayedObject> queue = new DelayQueue<>();
        try{
            DelayedObject o = queue.take();//此方法会阻塞
        }catch(InterruptExcetion e){
            e.printStackTrace();
        }