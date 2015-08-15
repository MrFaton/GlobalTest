package com.mr_faton.log4j_test;

import org.apache.log4j.Logger;

public class LoggerTest {
    private static final String CURRENT_CLASS_NAME = Thread.currentThread().getStackTrace()[1].getClassName();
    private static final Logger logger = Logger.getLogger(CURRENT_CLASS_NAME);

    public static void main(String[] args) {
        logger.debug("start program");
        callMe(10);
    }

    private static void callMe(int arg) {
        logger.info("run first method");
        System.out.println("argument = " + arg);
    }
}
