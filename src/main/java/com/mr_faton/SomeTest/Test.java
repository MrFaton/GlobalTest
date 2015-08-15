package com.mr_faton.SomeTest;

import java.util.logging.Logger;

public class Test {
    private static final Logger logger = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

    public static void main(String[] args) {
        System.out.println(logger.getName());
    }
}
