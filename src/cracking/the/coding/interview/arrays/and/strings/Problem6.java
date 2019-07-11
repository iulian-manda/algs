package cracking.the.coding.interview.arrays.and.strings;

public class Problem6 {

    public static String compress(String str) {
        if (str == null || str.length() < 2) return str;
        int count = 1;
        char current = str.charAt(0);
        StringBuilder builder = new StringBuilder("" + current);
        for (int i = 1; i < str.length(); i++) {
            char next = str.charAt(i);
            if (current != next) {
                builder.append(count);
                builder.append(next);
                count = 1;
                current = next;
            } else {
                count++;
            }
        }
        builder.append(count);
        return builder.length() < str.length() ? builder.toString() : str;
    }

    public static void main(String[] args) {
        System.out.println(compress("aabcccccaaa"));
        System.out.println(compress("ab"));
        System.out.println(compress("abdeeeeee"));
    }

}
