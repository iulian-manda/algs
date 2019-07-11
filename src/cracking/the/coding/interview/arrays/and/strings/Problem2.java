package cracking.the.coding.interview.arrays.and.strings;

import java.util.Arrays;

public class Problem2 {

    public static boolean isUnique(String s) {
        boolean[] frequency = new boolean[256];
        for (char c : s.toCharArray()) {
            int index = c - 'a';
            if (frequency[index]) return false;
            frequency[index] = true;
        }
        return true;
    }

    public static boolean isUniqueNoDataStructure(String s) {
        char[] array = s.toCharArray();
        Arrays.sort(array);
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == array[i + 1]) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(isUnique("abcdefg"));
        System.out.println(isUnique("abcdefa"));
        System.out.println(isUnique("abaaaaaa"));

        System.out.println(isUniqueNoDataStructure("abcdefg"));
        System.out.println(isUniqueNoDataStructure("abcdefa"));
        System.out.println(isUniqueNoDataStructure("abaaaaaa"));
    }
}
