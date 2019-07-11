package cracking.the.coding.interview.hard;

import java.util.Arrays;
import java.util.Random;

public class Problems1to3 {
    public static int add(int a, int b) {
        while (b != 0) {
            int sum = a ^ b;
            int carry = (a & b) << 1;
            a = sum;
            b = carry;
        }
        return a;
    }

    public static void shuffle(int[] deck) {
        Random rand = new Random();
        for (int i = 0; i < deck.length; i++) {
            int j = rand.nextInt(i + 1);
            int temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    public static int[] randomSubset(int[] set, int m) {
        Random rand = new Random();
        int[] subset = Arrays.copyOf(set, m);
        for (int i = m; i < set.length; i++) {
            int j = rand.nextInt(i + 1);
            if (j < m) {
                subset[j] = set[i];
            }
        }
        return subset;
    }
}
