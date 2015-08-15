package com.mr_faton.SomeTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, SQLException, IOException {
        Properties prop = new Properties();
        prop.put("JDBC_URL", "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123");
        prop.store(new FileOutputStream("Settings.properties"), "URL");
    }
}
