package cracking.the.coding.interview.moderate;

public class Problem7 {
    public static int max(int a, int b) {
        int k = (b - a) >>> 31;
        int notK = ~(b - a) >>> 31;
        return a * k + b * notK;
    }

    public static void main(String[] args) {
        System.out.println(max(-2, -7));
    }
}
