import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private final Integer[] index;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        int n = s.length();
        char[] a = s.toCharArray();

        index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        Arrays.sort(index, (Integer x, Integer y) -> {
            int i = x;
            int j = y;
            for (int k = 0; k < n; k++) {
                if (a[i] < a[j]) return -1;
                else if (a[i] > a[j]) return 1;
                else {
                    i = (i + 1) % n;
                    j = (j + 1) % n;
                }
            }
            return 0;
        });
    }

    public int length() {
        return index.length;
    }

    public int index(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException();
        }
        return index[i];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        CircularSuffixArray csf = new CircularSuffixArray(in.readAll());
        StdOut.println(csf.length());
        for (int i = 0; i < csf.length(); i++) {
            StdOut.print(csf.index(i) + " ");
        }
    }
}
