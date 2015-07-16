package com.mr_faton.SomeTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, SQLException {
//        String str = "Понравился фильм Терминатор: Генезис. Все очень динамично, не много предсказуемо местами,но я ни разу не заскучала. Хороший фильм и актеры \uD83D\uDC4D";
        String str = "@Я simple – коралл! Встречайте День Земли: найдите свое животное с помощью викторины #GoogleDoodle.";
        String upStr = str.replaceAll("[^A-Za-zА-Яа-я0-9\\p{Punct}\\s\\n\\r\\t\\v]", "");
        System.out.println(upStr);
    }
}
