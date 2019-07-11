package cracking.the.coding.interview.arrays.and.strings;

public class Problem5 {

    public static boolean isOneAway(String first, String second) {
        if (Math.abs(first.length() - second.length()) > 1) return false;
        String s1 = first.length() < second.length() ? first : second;
        String s2 = first.length() < second.length() ? second : first;
        int i1 = 0, i2 = 0;
        boolean foundDifference = false;
        while (i1 < s1.length() && i2 < s2.length()) {
            if (s1.charAt(i1) != s2.charAt(i2)) {
                if (foundDifference) return false;
                foundDifference = true;
                if (s1.length() == s2.length()) i1++;
            } else {
                i1++;
            }
            i2++;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isOneAway("pale", "ple"));
        System.out.println(isOneAway("pales", "pale"));
        System.out.println(isOneAway("pale", "bale"));
        System.out.println(isOneAway("pale", "bake"));
    }

}
