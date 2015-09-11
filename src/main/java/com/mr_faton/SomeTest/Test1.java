package com.mr_faton.SomeTest;

import java.util.Calendar;

/**
 * Created by Mr_Faton on 11.09.2015.
 */
public class Test1 {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        calendar.add(Calendar.MINUTE, 10);
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }
}
