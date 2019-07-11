package cracking.the.coding.interview.arrays.and.strings;

public class Problem3 {

    public static void URLify(char[] s, int length) {
        int urlIndex = s.length - 1;
        int sIndex = length - 1;
        while (sIndex > 0) {
            if (s[sIndex] == ' ') {
                s[urlIndex--] = '0';
                s[urlIndex--] = '2';
                s[urlIndex--] = '%';
            } else {
                s[urlIndex--] = s[sIndex];
            }
            sIndex--;
        }
    }

    public static void main(String[] args) {
        char[] s = "Mr John Smith    ".toCharArray();
        URLify(s, 13);
        System.out.println(s);
    }

}
