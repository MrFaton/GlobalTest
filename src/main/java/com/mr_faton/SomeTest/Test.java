package com.mr_faton.SomeTest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("abc");
        arrayList.add("def");
        arrayList.add("ghj");

        for (int i = 0; i < 10; i++) {
            System.out.println((int) (Math.random() * arrayList.size()));
        }
    }
}
