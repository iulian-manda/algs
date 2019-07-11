package cracking.the.coding.interview.bit.manipulation;

public class Problem1 {

    static int insert(int n, int m, int i, int j) {
        int allOnes = ~0;
        int left = allOnes << (j + 1);
        int right = (1 << i) - 1;
        int mask = left | right;

        int n_cleared = n & mask;
        int m_shifted = m << i;
        return n_cleared | m_shifted;
    }

    public static void main(String[] args) {
        int n = 0b10000111000;
        int m = 0b10011;
        int result = insert(n, m, 2, 6);
        System.out.println(Integer.toBinaryString(result));
    }

}
