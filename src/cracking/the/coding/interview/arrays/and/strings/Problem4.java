package cracking.the.coding.interview.arrays.and.strings;

public class Problem4 {

    static int numChars = 128;

    public static boolean isPalindromePermutation(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        int[] freq = new int[numChars];
        for (char c : chars) {
            if (c != ' ') freq[c]++;
        }
        boolean odd = false;
        for (int i = 0; i < numChars; i++) {
            if (freq[i] % 2 != 0) {
                if (odd) return false;
                odd = true;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(isPalindromePermutation("Tact Coa"));
    }
}
