package com.mr_faton.SomeTest;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;

public class Test {
    private static final String fileName = "ttt.properties";

    public static void main(String[] args) {
        ClassLoader loader = Test.class.getClassLoader();
        InputStream in = loader.getResourceAsStream(fileName);
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
