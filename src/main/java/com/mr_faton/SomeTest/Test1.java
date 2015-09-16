package com.mr_faton.SomeTest;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Mr_Faton on 11.09.2015.
 */
public class Test1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        int num = 5;
        int[] arr = {2, 5, 4, 9, 0, 5, 6, 8};
        System.out.println("source arr " + Arrays.toString(arr));
        System.out.println("source arr length " + arr.length);
        int[] resultArr = delNum(num, arr);
        System.out.println("result arr " + Arrays.toString(resultArr));
        System.out.println("result arr length " + resultArr.length);
    }

    private static int[] delNum(int num, int[] arr) {
        int[] updatedArr = new int[arr.length];
        int updatedArrNum = 0;
        int totalDel = 0;
        for (int cur : arr) {
            if (cur == num) {
                totalDel++;
                continue;
            }
            updatedArr[updatedArrNum++] = cur;
        }
        int[] resultArr = new int[updatedArr.length - totalDel];
        System.arraycopy(updatedArr, 0, resultArr, 0, updatedArr.length - totalDel);
        return resultArr;
    }
}