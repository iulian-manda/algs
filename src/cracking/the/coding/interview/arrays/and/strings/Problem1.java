package cracking.the.coding.interview.arrays.and.strings;

public class Problem1 {

    static int noChars = 256;

    public static boolean checkPermutation(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;
        int[] freq1 = new int[noChars];
        int[] freq2 = new int[noChars];
        for (int i = 0; i < s1.length(); i++) {
            freq1[s1.charAt(i)]++;
            freq2[s2.charAt(i)]++;
        }
        for (int i = 0; i < noChars; i++) {
            if (freq1[i] != freq2[i]) return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(checkPermutation("abcdef", "bcdafe"));
        System.out.println(checkPermutation("abcdef", "bcdafes"));
        System.out.println(checkPermutation("abcdeaf", "bcdasfe"));
    }
}
