package com.mr_faton.SomeTest;

import java.util.concurrent.*;

public class ExecutorManagerReal {
    //пул потоков
    private static ExecutorService threadPool;
    //блокирующая очередь в случае если задачи прибывают, а исполнимые потоки кончились
    private static BlockingQueue<Runnable> blockingQueue;
    //размер блокирующей очереди
    private static final int QUEUE_SIZE = 50;
    //максимальное колличество потоков в пуле
    private static final int MAX_POOL_SIZE = 4;
    //колличество корневых потоков в пуле
    private static final int ROOT_THREADS = 1;
    //время жизни потока
    private static final long THREAD_LIFE_TIME = 3L;

    public ExecutorManagerReal() {
        blockingQueue = new ArrayBlockingQueue<>(QUEUE_SIZE, true);
        threadPool =
                new ThreadPoolExecutor(ROOT_THREADS, MAX_POOL_SIZE, THREAD_LIFE_TIME, TimeUnit.MINUTES, blockingQueue);
    }

    public void handle() {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                double d = 2;
                for (int k = 0; k < 10_000_000; k++) {
                    d = Math.sin(d);
                }
                System.out.println("done " + Thread.currentThread().getName());
            }
        });
    }

    public void shutDown() {
        threadPool.shutdown();
    }
}
