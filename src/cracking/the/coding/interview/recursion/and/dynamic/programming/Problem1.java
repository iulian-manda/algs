package cracking.the.coding.interview.recursion.and.dynamic.programming;

import java.util.Arrays;

public class Problem1 {

    static int tripleStep(int n, int[] memo) {
        if (n < 0) return 0;
        if (n == 0) return 1;
        if (memo[n] == -1) memo[n] = tripleStep(n - 1, memo) + tripleStep(n - 2, memo) + tripleStep(n - 3, memo);
        return memo[n];
    }

    static int tripleStep(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return tripleStep(n, memo);
    }

    public static void main(String[] args) {
        System.out.println(tripleStep(4));
        System.out.println(tripleStep(5));
    }

}
