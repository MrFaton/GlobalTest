package com.mr_faton.interview;

/**
 * Description
 *
 * @author root
 * @since 25.11.2015
 */
public class StringReplaceDeleteSymbol {
    public static void main(String[] args) {
        String original = "dog";
        String updated = delete(original, 'o');
        System.out.println("original = " + original);
        System.out.println("updated = " + updated);

        System.out.println();

        updated = replace(original, 'o', 'i');
        System.out.println("original = " + original);
        System.out.println("updated = " + updated);

        System.out.println();


    }

    public static String delete(String str, char replacement) {
        if (str == null) throw new IllegalArgumentException("str can't be null");
        char[] arr = str.toCharArray();
        char[] updatedArr = new char[str.length()];
        int deletions = 0;
        int curPosToInsert = 0;
        for (char original : arr) {
            if (original == replacement) {
                deletions++;
                continue;
            }
            updatedArr[curPosToInsert] = original;
            curPosToInsert++;
        }
        return new String(updatedArr, 0, str.length() - deletions);
    }
    public static String replace(String str, char replacement, char actual) {
        if (str == null) throw new IllegalArgumentException("str can't be null");
        char[] arr = str.toCharArray();
        char[] updatedArr = new char[str.length()];
        for (int i = 0; i < arr.length; i++) {
            char original = arr[i];
            if (original == replacement) {
                original = actual;
            }
            updatedArr[i] = original;
        }
        return new String(updatedArr);
    }
//    public static int[] delete(int[] baseArr, int num) {
//        if (baseArr.length == 0) return baseArr;
//
//    }
}
