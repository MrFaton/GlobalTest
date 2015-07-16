package com.mr_faton.db_examples;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Mr_Faton on 16.07.2015.
 */
public class InsertIfNotExist {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123";//DB at home
        String DB_NAME = "tweagle";
        String TABlE_TWITTER_MESSAGES_N = "twitter_messages";

        String MESSAGE = "message";
        String TYPE = "type";
        String OWNER = "owner";
        String OWNER_GENDER = "owner_gender";
        String RECIPIENT = "recipient";
        String POSTED_DATE = "posted_date";
        String SYNONYMISED = "synonymised";

        java.util.Date donorMessageDate = new java.util.Date();
        String donorMessage = "@Dorable_Dimples: Okay enough of those #IfYouWereMines I'm getting depressed. #foreveralone ?";
        String type = "tweet";
        String donorAccount = "Mr_Faton";
        String donorGender = "male";
        String recipient = null;

        String sql = "" +
                "INSERT INTO " +
                DB_NAME + "." + TABlE_TWITTER_MESSAGES_N + " " +
                "(" +
                MESSAGE + ", " +
                TYPE + ", " +
                OWNER + ", " +
                OWNER_GENDER + ", " +
                RECIPIENT + ", " +
                POSTED_DATE + ", " +
                SYNONYMISED +
                ")" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        java.sql.Date messageDate = new java.sql.Date(donorMessageDate.getTime());

        Connection connection = DriverManager.getConnection(JDBC_URL);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, donorMessage);
        preparedStatement.setString(2, type);
        preparedStatement.setString(3, donorAccount);
        preparedStatement.setString(4, donorGender);
        preparedStatement.setString(5, recipient);
        preparedStatement.setDate(6, messageDate);
        preparedStatement.setBoolean(7, false);

        System.out.println(preparedStatement);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
