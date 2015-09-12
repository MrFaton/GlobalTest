package com.mr_faton.SomeTest;

/**
 * Created by root on 12.09.2015.
 */
public class Test2 {
    public static void main(String[] args) {
        System.out.println(convertTime(12317000));
    }
    private static String convertTime(long originalTime) {
        long s = (originalTime / 1000) % 60;
        long m = (originalTime / 1000 / 60) % 60;
        long h = (originalTime / 1000 / 60 / 60) % 24;
        return String.format("%02d:%02d:%02d", h,m,s);
    }
}
