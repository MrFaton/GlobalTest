package com.mr_faton.db_push;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class ReplaceSynonyms2 {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/tweagle?user=Mr_Faton&password=123";//DB at home
    private static final String DB_NAME = "tweagle";
    private static final String TABLE_NAME = "new_synonyms";
    private static final String SQL = "SELECT synonyms FROM " + DB_NAME + "." + TABLE_NAME + " WHERE word=?;";

    private static final int MIN_SYN_WORD_LENGTH = 3;
    private static final int MIN_SYN_PERCENT = 30;
    private static final int MAX_SYN_PERCENT = 80;

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;

    public static void main(String[] args) throws SQLException {
        String message = "давайте вы будете любить меня , а я не обязательно";
        String updatedMessage = "";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL);

            List<String> words = getWordList(message);//1
            List<Integer> passableReplacementsList = getPositionsOfPassableReplacement(words);//2
            int replacementsCount = getReplacementsCount(passableReplacementsList);//3
            replaceSynonyms(words, replacementsCount, passableReplacementsList);//4

            updatedMessage = getUpdatedMessage(words);

            preparedStatement.close();
            connection.close();

        } catch (Exception ex) {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
            ex.printStackTrace();
        }

        System.out.println(message + " - original");
        System.out.println(updatedMessage + " - result");
    }

    private static String getUpdatedMessage(List<String> words) {
        String updatedMessage = "";
        for (String word : words) {
            updatedMessage += word + " ";
        }

        return updatedMessage.trim();
    }

    private static void replaceSynonyms(List<String> words, int replacementsCount, List<Integer> passableReplacementsList) throws SQLException {
        int currentReplacementNum = 0;//4.1
        while (currentReplacementNum < replacementsCount && !passableReplacementsList.isEmpty()) {//4.2
            int indexOfReplacementWordIndex = (int) (Math.random() * passableReplacementsList.size());//4.3
            int replacementWordIndex = passableReplacementsList.get(indexOfReplacementWordIndex);//4.4
            String replacementWord = words.get(replacementWordIndex);//4.5
            List<String> synonyms = getSynonymList(replacementWord);//4.6
            passableReplacementsList.remove(indexOfReplacementWordIndex);//4.7
            if (synonyms.isEmpty()) {//4.8
                continue;
            }
            String synonym = getSynonym(synonyms);//4.9
            words.set(replacementWordIndex, synonym);//4.10
            currentReplacementNum++;
        }
    }

    private static int getReplacementsCount(List<Integer> passableReplacementsList) {
        int randomPercent = MIN_SYN_PERCENT + (int) (Math.random() * ((MAX_SYN_PERCENT - MIN_SYN_PERCENT) + 1));
        int passableReplacementsCount = passableReplacementsList.size();
        return (int) ((double)passableReplacementsCount / (double)100 * (double)randomPercent);
    }

    private static List<Integer> getPositionsOfPassableReplacement(List<String> messageWordsList) {
        ArrayList<Integer> passableReplacements = new ArrayList<>();
        for (int i = 0; i < messageWordsList.size(); i++) {
            String synonym = messageWordsList.get(i);
            if (synonym.length() > MIN_SYN_WORD_LENGTH) {
                passableReplacements.add(i);
            }
        }
        if (passableReplacements.isEmpty()) {
            return null;
        }
        return passableReplacements;
    }

    private static List<String> getWordList(String message) {
        ArrayList<String> words = new ArrayList<>();
        String[] messageTokens = message.split(" ");
        for (String word : messageTokens) {
            words.add(word);
        }
        return words;
    }

    private static List<String> getSynonymList(String word) throws SQLException {
        preparedStatement.setString(1, word);
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<String> synonymList = new ArrayList<>();
        if (resultSet.next()) {
            String synonyms = resultSet.getString(1);
            String[] synonymTokens = synonyms.split(",");
            for (String synonym : synonymTokens) {
                synonymList.add(synonym);
            }
        }
        resultSet.close();
        return synonymList;
    }

    private static String getSynonym(List<String> synonymList) {
        int wordNum = (int) (Math.random() * synonymList.size());
        return synonymList.get(wordNum);
    }
}
/*
Like a first variant, only without log and comments
 */