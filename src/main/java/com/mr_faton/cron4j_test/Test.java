package com.mr_faton.cron4j_test;

import it.sauronsoftware.cron4j.Predictor;
import it.sauronsoftware.cron4j.Scheduler;

/**
 * Description
 *
 * @author root
 * @version 1.0
 * @since 02.10.2015
 */
public class Test {
    public static void main(String[] args) {
        String pattern = "30-40/2 * * * *";
        Predictor predictor = new Predictor(pattern);

        System.out.println(String.format("%tH:%<tM:%<tS", predictor.nextMatchingDate()));

        Scheduler scheduler = new Scheduler();

        scheduler.schedule(pattern, new Runnable() {
            @Override
            public void run() {
                System.out.println("bep");
            }
        });

        scheduler.start();


        System.out.println("main end");
    }
}