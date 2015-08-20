package com.mr_faton.SomeTest;


import java.util.concurrent.*;

public class Test {
    private static ThreadPoolExecutor threadPool;
    //блокирующая очередь в случае если задачи прибывают, а исполнимые потоки кончились
    private static BlockingQueue<Runnable> blockingQueue;
    //размер блокирующей очереди
    private static final int QUEUE_SIZE = 2;
    //максимальное колличество потоков в пуле
    private static final int MAX_POOL_SIZE = 10;
    //колличество корневых потоков в пуле
    private static final int ROOT_THREADS = 2;
    //время жизни потока
    private static final long THREAD_LIFE_TIME = 3L;


    public static void main(String[] args) throws InterruptedException {
        blockingQueue = new ArrayBlockingQueue<>(QUEUE_SIZE, true);
//        threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        threadPool.setCorePoolSize(1);
//        threadPool.setMaximumPoolSize(MAX_POOL_SIZE);
//        threadPool.setKeepAliveTime(5, TimeUnit.SECONDS);

        threadPool = new ThreadPoolExecutor(
                ROOT_THREADS, MAX_POOL_SIZE, THREAD_LIFE_TIME, TimeUnit.SECONDS, blockingQueue
        );






        for (int i = 0; i < 9; i++) {
            threadPool.execute(new Task());
        }

        for (int i = 0; i < 60_000; i++) {
            System.out.println("pool size" + threadPool.getPoolSize());
            System.out.println("queue size" + threadPool.getQueue().size());
            Thread.sleep(400);
        }

        threadPool.shutdown();
    }
}
class Task implements Runnable {
    public static int COUNTER = 0;

    public Task() {
        COUNTER++;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        double d = 2;
        for (int k = 0; k < 60_000_000; k++) {
            d = Math.sin(d);
        }
        System.out.println("done " + Thread.currentThread().getName());
    }

    @Override
    public String toString() {
        return "Task#" + COUNTER;
    }
}