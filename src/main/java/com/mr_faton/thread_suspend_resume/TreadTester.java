package com.mr_faton.thread_suspend_resume;

public class TreadTester {
    public static void main(String[] args) throws InterruptedException {
        ColorThread colorThread = new ColorThread();
        Thread t1 = new Thread(colorThread);
        System.out.println("before start");
        t1.start();
        System.out.println("after start");

        Thread.sleep(3000);
        System.out.println("suspend");
        colorThread.suspend();
        System.out.println("thread suspended");
        Thread.sleep(2000);
        System.out.println("resume");
        colorThread.resume();
    }
}

class ColorThread implements Runnable{
    boolean suspendFlag = false;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    synchronized void suspend() {
        suspendFlag = true;
    }
    synchronized void resume() {
        suspendFlag = false;
        notifyAll();
    }
}
