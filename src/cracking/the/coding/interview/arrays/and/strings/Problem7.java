package cracking.the.coding.interview.arrays.and.strings;

public class Problem7 {

    public static boolean rotate90(int[][] m) {
        if (m.length == 0 || m.length != m[0].length) return false;
        int n = m.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = m[first][i];
                m[first][i] = m[last - offset][first];
                m[last - offset][first] = m[last][last - offset];
                m[last][last - offset] = m[i][last];
                m[i][last] = top;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int[][] m = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int n = m.length;
//        print(m, n);
        rotate90(m);
        print(m, n);
    }

    private static void print(int[][] m, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }


}
