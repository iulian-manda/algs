package cracking.the.coding.interview.moderate;

public class Problem5 {

    public static int factorialZeros(int n) {
        int div2 = 0, div5 = 0, div10 = 0;
        for (int i = 2; i <= n; i++) {
            if (i % 10 == 0) div10++;
            else if (i % 5 == 0) div5++;
            else if (i % 2 == 0) div2++;
        }
        int min = Math.min(div2, div5);
        return min + div10;
    }

    public static long factorial(int n) {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static void main(String[] args) {
        System.out.println(factorialZeros(50));
    }
}
