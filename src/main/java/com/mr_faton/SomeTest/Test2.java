package com.mr_faton.SomeTest;

import java.util.Calendar;

/**
 * Created by root on 12.09.2015.
 */
public class Test2 {
    public static void main(String[] args) {
        System.out.println(resolve());
    }

    private static long resolve() {
        int beginHour = 2;
        int beginMin = 15;
        int periodInMin = 5;

        Calendar workTime = Calendar.getInstance();
        workTime.set(Calendar.HOUR_OF_DAY, beginHour);
        workTime.set(Calendar.MINUTE, beginMin);

        long currentTime = System.currentTimeMillis();

        while (workTime.getTimeInMillis() <= currentTime) {
            workTime.add(Calendar.MINUTE, periodInMin);
        }

        return workTime.getTimeInMillis() - currentTime;
    }

}
