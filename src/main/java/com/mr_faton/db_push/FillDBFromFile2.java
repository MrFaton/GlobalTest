package com.mr_faton.db_push;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class FillDBFromFile2 {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123";//DB at home
    private static final String DB_NAME = "tweagle";
    private static final String TABLE_NAME = "new_synonyms";
    private static final String SQL = "INSERT INTO " + DB_NAME + "." + TABLE_NAME + " (word, synonyms) VALUES (?, ?);";
    private static final String FILE_PATH = "C:\\Synonym.txt";
    private static final int MAX_WORD_STR_LENGTH = 35;
    private static final int MAX_SYNONYM_STR_LENGTH = 60;
    private static final String DELIMITER_OLD = "|";
    private static final String DELIMITER_NEW = ",";
    private static int CURRENT_LINE = 0;
    private static int PROGRESS = 0;
    private static int LINE_NUM = 1_262_744;

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String line = null;
        String word = null;
        String synonyms = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL);

            try(Scanner scanner = new Scanner(new FileInputStream(FILE_PATH), "cp1251")) {
                while (scanner.hasNextLine()) {
                    CURRENT_LINE++;
                    line = scanner.nextLine();
                    int delimiterIndex = line.indexOf(DELIMITER_OLD);
                    word = line.substring(0, delimiterIndex);
                    if (word.contains(" ")) {
                        continue;
                    }
                    if (word.length() > MAX_WORD_STR_LENGTH) {
                        continue;
                    }
                    //the difference! Ignore words that have many synonyms
                    if (line.length() - word.length() > MAX_SYNONYM_STR_LENGTH) {
                        continue;
                    }

                    synonyms = line.substring(++delimiterIndex, line.length());

                    synonyms = synonyms.replace(DELIMITER_OLD, DELIMITER_NEW);

                    preparedStatement.setString(1, word);
                    preparedStatement.setString(2, synonyms);
                    preparedStatement.executeUpdate();

                    int currentProgress = (int) (((double)CURRENT_LINE / (double)LINE_NUM) * 100);
                    if (currentProgress != PROGRESS) {
                        PROGRESS = currentProgress;
                        System.out.println(PROGRESS + "%");
                    }
                }
            }



            connection.commit();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
            System.err.println("line=" + line);
            System.err.println("word=" + word);
            System.err.println("synonyms=" + synonyms);
            e.printStackTrace();
        }
    }
}
