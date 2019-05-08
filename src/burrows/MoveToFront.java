import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int R = 256;

    public static void encode() {
        char[] seq = initCharSeq();
        while (!BinaryStdIn.isEmpty()) {
            char in = BinaryStdIn.readChar();
            char out = findChar(seq, in);
            BinaryStdOut.write(out);
            moveToFront(seq, out);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        char[] seq = initCharSeq();
        while (!BinaryStdIn.isEmpty()) {
            char in = BinaryStdIn.readChar();
            char out = seq[in];
            BinaryStdOut.write(out);
            moveToFront(seq, in);
        }
        BinaryStdOut.close();
    }

    private static char[] initCharSeq() {
        char[] seq = new char[R];
        for (char i = 0; i < R; i++) {
            seq[i] = i;
        }
        return seq;
    }

    private static char findChar(char[] seq, char c) {
        for (char i = 0; i < R; i++) {
            if (c == seq[i]) return i;
        }
        throw new IllegalArgumentException("Char " + c + " is outside of the extended ASCII range!");
    }

    private static void moveToFront(char[] seq, char index) {
        for (char i = index; i > 0; i--) {
            char temp = seq[i];
            seq[i] = seq[i - 1];
            seq[i - 1] = temp;
        }
    }


    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        }
    }

}
