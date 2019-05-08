import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256;

    public static void transform() {
        String text = BinaryStdIn.readString();
        CircularSuffixArray csf = new CircularSuffixArray(text);
        BinaryStdOut.write(findFirst(csf));
        for (int i = 0; i < text.length(); i++) {
            int index = csf.index(i);
            index--;
            if (index == -1) index = text.length() - 1;
            BinaryStdOut.write(text.charAt(index));
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String text = BinaryStdIn.readString();

        int n = text.length();
        int[] next = new int[n];
        char[] tn = text.toCharArray();
        char[] t1 = new char[n];
        int[] count = sort(tn);
        for (int i = 0; i < n; i++) {
            next[count[tn[i]]] = i;
            t1[count[tn[i]]++] = tn[i];
        }
        BinaryStdOut.write(t1[first]);
        int current = next[first];
        for (int i = 1; i < n; i++) {
            BinaryStdOut.write(t1[current]);
            current = next[current];
        }
        BinaryStdOut.close();
    }

    private static int[] sort(char[] a) {
        int[] count = new int[R+1];
        int n = a.length;
        for (int i = 0; i < n; i++) {
            count[a[i]+1]++;
        }
        for (int i = 0; i < R; i++) {
            count[i+1] += count[i];
        }
        return count;
    }

    private static int findFirst(CircularSuffixArray csf) {
        for (int i = 0; i < csf.length(); i++) {
            if (csf.index(i) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }

}
