package permutation;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = newArray(1);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == s.length) {
            resize(2 * s.length);
        }
        s[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        throwIfEmpty();
        swapLastWithRandom();
        Item item = s[--n];
        s[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        throwIfEmpty();
        swapLastWithRandom();
        return s[n - 1];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private Item[] newArray(int capacity) {
        return (Item[]) new Object[capacity];
    }

    private void resize(int capacity) {
        Item[] copy = newArray(capacity);
        for (int i = 0; i < n; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    private void swapLastWithRandom() {
        if (n < 1) return;
        int randomIndex = StdRandom.uniform(0, n);
        Item temp = s[n - 1];
        s[n - 1] = s[randomIndex];
        s[randomIndex] = temp;
    }

    private void throwIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        Item[] arr;
        int size;
        int current;

        public RandomizedQueueIterator() {
            size = size();
            arr = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                arr[i] = s[i];
            }
            for (int i = 0; i < size; i++) {
                randomSwap(i);
            }
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return arr[current++];
        }

        private void randomSwap(int i) {
            int randomIndex = StdRandom.uniform(0, i + 1);
            Item temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
    }

    // unit testing (optional)
    /*
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new FileInputStream(new File("/Users/iulian.manda/IdeaProjects/percolation/resources/permutation/duplicates.txt")));
        permutation.RandomizedQueue<String> randomizedQueue = new permutation.RandomizedQueue<>();
        permutation.RandomizedQueue<String> randomizedQueue1 = new permutation.RandomizedQueue<>();
        while (sc.hasNext()) {
            String item = sc.next();
            randomizedQueue.enqueue(item);
            randomizedQueue1.enqueue(item);
        }
        sc.close();
        for (String item : randomizedQueue) {
            System.out.print(item + " ");
        }
        System.out.println();
        for (String item : randomizedQueue1) {
            System.out.print(item + " ");
        }
        System.out.println();
        while (!randomizedQueue.isEmpty()) {
            System.out.print(randomizedQueue.dequeue() + " ");
        }
        System.out.println();
        while (!randomizedQueue1.isEmpty()) {
            System.out.print(randomizedQueue1.dequeue() + " ");
        }
        System.out.println();

    } */
}
