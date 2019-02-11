package permutation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            int random = StdRandom.uniform(1);
            if (random % 2 == 0) {
                if (queue.size() == k) {
                    queue.dequeue();
                }
                queue.enqueue(StdIn.readString());
            }
        }
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}