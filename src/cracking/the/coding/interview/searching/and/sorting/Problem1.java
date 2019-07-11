package cracking.the.coding.interview.searching.and.sorting;

import java.util.Arrays;

public class Problem1 {

    static void merge(int[] a, int[] b) {
        int rightA = a.length - 1;
        while (a[rightA] == -1) rightA--;
        int rightB = b.length - 1;
        int current = a.length - 1;
        while (rightA >= 0 && rightB >= 0) {
            if (a[rightA] > b[rightB]) a[current--] = a[rightA--];
            else a[current--] = b[rightB--];
        }
        while (rightB >= 0) a[current--] = b[rightB--];
    }

    public static void main(String[] args) {
        int[] a = {3, 5, 9, 11, 12, 16, -1, -1, -1, -1};
        int[] b = {1, 6, 10, 12};
        merge(a, b);
        System.out.println(Arrays.toString(a));
    }

}
