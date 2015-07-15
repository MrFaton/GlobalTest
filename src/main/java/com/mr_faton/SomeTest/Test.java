package com.mr_faton.SomeTest;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        String[] arr = {"2012 Привет", "Ого 2014 уже настопил", "20186", "1976 ujl", "asfj 34 klajsf 43 adkljf 232"};

        for (String text : arr) {
            System.out.println(text.replaceAll("2\\d{3}", "*"));
        }
    }
}
