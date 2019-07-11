package cracking.the.coding.interview.moderate;

import java.util.Arrays;

public class Problem6 {

    public static int smallestDifference(int[] array1, int[] array2) {
        int[] a = Arrays.copyOf(array1, array1.length);
        int[] b = Arrays.copyOf(array2, array2.length);
        Arrays.sort(a);
        Arrays.sort(b);
        int i = 0;
        int j = 0;
        int min = Integer.MAX_VALUE;
        while (i < a.length && j < b.length) {
            int diff = Math.abs(a[i] - b[j]);
            if (diff < min) min = diff;
            if (a[i] < b[j]) i++;
            else if (a[i] > b[j]) j++;
            else return 0;

        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(smallestDifference(new int[]{1, 3, 15, 11, 2}, new int[]{23, 127, 235, 19, 8}));
    }
}