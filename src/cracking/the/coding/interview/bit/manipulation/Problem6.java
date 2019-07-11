package cracking.the.coding.interview.bit.manipulation;

public class Problem6 {
    static int conversion(int a, int b) {
        int count = 0;
        for (int c = a ^ b; c != 0; c &= c - 1) {
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int a = 0b11101;
        int b = 0b01111;
        System.out.println(conversion(a, b));
    }
}
