package com.mr_faton.db_push;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Faton on 09.07.2015.
 */
public class ReplaceSynonyms {
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
        String message = "Погода в Минске последнее время напоминает мне Лондон. Почти каждый вечер дождь";
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
        System.out.println("getUpdatedMessage ->");
        String updatedMessage = "";
        for (String word : words) {
            updatedMessage += word + " ";
        }

        return updatedMessage.trim();
    }

    private static void replaceSynonyms(List<String> words, int replacementsCount, List<Integer> passableReplacementsList) throws SQLException {
        System.out.println("replaceSynonyms ->");
        System.out.println("replaceSynonyms -> words=" + words);
        System.out.println("replaceSynonyms -> replacementsCount=" + replacementsCount);
        System.out.println("replaceSynonyms -> passableReplacementsList=" + passableReplacementsList);
        int currentReplacementNum = 0;//4.1
        while (currentReplacementNum < replacementsCount && !passableReplacementsList.isEmpty()) {//4.2
            System.out.println("replaceSynonyms -> try to replace...");

            int indexOfReplacementWordIndex = (int) (Math.random() * passableReplacementsList.size());//4.3
            System.out.println("replaceSynonyms -> indexOfReplacementWordIndex=" + indexOfReplacementWordIndex);

            int replacementWordIndex = passableReplacementsList.get(indexOfReplacementWordIndex);//4.4
            System.out.println("replaceSynonyms -> replacementWordIndex=" + replacementWordIndex);

            String replacementWord = words.get(replacementWordIndex);//4.5
            System.out.println("replaceSynonyms -> replacementWord=" + replacementWord);

            List<String> synonyms = getSynonymList(replacementWord);//4.6
            System.out.println("replaceSynonyms -> synonyms=" + synonyms);

            passableReplacementsList.remove(indexOfReplacementWordIndex);//4.7
            if (synonyms.isEmpty()) {//4.8
                continue;
            }
            String synonym = getSynonym(synonyms);//4.9
            System.out.println("replaceSynonyms -> synonym=" + synonym);

            words.set(replacementWordIndex, synonym);//4.10
            currentReplacementNum++;
            System.out.println("replaceSynonyms -> currentReplacementNum=" + currentReplacementNum);
        }
    }

    //how much replacements do we need to do
    private static int getReplacementsCount(List<Integer> passableReplacementsList) {
        System.out.println("getReplacementsCount -> ");

        int randomPercent = MIN_SYN_PERCENT + (int) (Math.random() * ((MAX_SYN_PERCENT - MIN_SYN_PERCENT) + 1));
        System.out.println("getReplacementsCount -> randomPercent=" + randomPercent);

        int passableReplacementsCount = passableReplacementsList.size();
        System.out.println("getReplacementsCount -> passableReplacementsCount=" + passableReplacementsCount);
        return (int) ((double)passableReplacementsCount / (double)100 * (double)randomPercent);
    }

    //word indexes from list "word", which passable can be replaced
    private static List<Integer> getPositionsOfPassableReplacement(List<String> messageWordsList) {
        System.out.println("getPositionsOfPassableReplacement -> ");
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
        System.out.println("getPositionsOfPassableReplacement -> passableReplacements=" + passableReplacements);
        return passableReplacements;
    }

    private static List<String> getWordList(String message) {
        System.out.println("getWordList -> ");
        ArrayList<String> words = new ArrayList<>();
        String[] messageTokens = message.split(" ");
        for (String word : messageTokens) {
            words.add(word);
        }
        return words;
    }

    private static List<String> getSynonymList(String word) throws SQLException {
        System.out.println("getSynonymList -> ");
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
        System.out.println("getSynonymList -> synonymList=" + synonymList);
        return synonymList;
    }

    private static String getSynonym(List<String> synonymList) {
        System.out.println("getSynonym -> ");
        int wordNum = (int) (Math.random() * synonymList.size());
        System.out.println("getSynonym -> wordNum=" + wordNum);
        return synonymList.get(wordNum);
    }
}
/*
Working logic
1 - take message and split it on words and store it to list. Call this list like "words"

2 - analise this list and identify words that length is bigger than MIN_SYN_WORD_LENGTH. The result of analysing not a
list of founded words - the result is the indexes of this words. We have list of indexes which pointed on the words from
list, which the result of first step. Simple we have a list with indexes who pointed on the words from list "words".
Call this list "indexesOfReplacementWordsIndexList"

3 - get a number of actually replacement. This method evaluate real number of needed replacements. How it's work?
First it takes minimum and maximum replacement percent and randomly evaluate value between this minimum and maximum.
At the second it takes number of possible replacements through getting size of "indexesOfReplacementWordsIndexList"
(list with all possible replacements).
Third step is get and return actually replacement number through evaluating percentage of all possible replacements.
For example: we have 9 possible replacements and a percent of replace is 40%. This method returns 4 actual replacement.

4 - Stage of replacement.
4.1 - current number of replacement (how many replacements we have done)
4.2 - work until current replacements equals needed replacement or possible replacement list
(indexesOfReplacementWordsIndexList) is not empty (because in DB can not to be synonym of current word)
4.3 take a random index from indexesOfReplacementWordsIndexList (from zero to indexesOfReplacementWordsIndexList.length).
4.4 take an index of replacement word (this index is pointed on "words" list)
4.5 take a word for replacement by index from 4.4
4.6 take a synonym list for word from 4.5
4.7 delete used index from indexesOfReplacementWordsIndexList
4.8 if synonym list is empty so we can't to do replace that why continue
4.9 take random synonym from list
4.10 replace word from "words" list by it's index taken from a indexesOfReplacementWordsIndexList
 */