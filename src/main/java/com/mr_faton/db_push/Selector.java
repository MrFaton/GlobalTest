package com.mr_faton.db_push;

import java.sql.*;

public class Selector {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123";
        String sql = "SELECT * FROM tweagle.synonyms;";
        Connection connection = DriverManager.getConnection(JDBC_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String word = resultSet.getString("word");
            String synonyms = resultSet.getString("synonyms");
            if (synonyms.length() > 60) {
                System.out.println(word);
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
