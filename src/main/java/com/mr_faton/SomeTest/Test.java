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
        String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123";//DB at home
        String sql = "SELECT posted_date FROM tweagle.twitter_messages WHERE id=1;";

        Connection connection = DriverManager.getConnection(JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        java.sql.Date sqlDate = null;
        if (resultSet.next()) {
            sqlDate = resultSet.getDate(1);
        }

        System.out.println(sqlDate);

        java.util.Date utilDate = sqlDate;
        System.out.println(utilDate);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(utilDate);

        System.out.println(calendar.get(Calendar.YEAR));
    }
}
