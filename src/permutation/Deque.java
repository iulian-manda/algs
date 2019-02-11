package permutation;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() { }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        throwIfNull(item);
        size++;
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        if (first != null) {
            first.prev = newFirst;
        } else {
            last = newFirst;
        }
        first = newFirst;
    }

    // add the item to the end
    public void addLast(Item item) {
        throwIfNull(item);
        size++;
        Node newLast = new Node();
        newLast.item = item;
        newLast.prev = last;
        if (last != null) {
            last.next = newLast;
        } else {
            first = newLast;
        }
        last = newLast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        throwIfEmpty();
        size--;
        Item result = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        throwIfEmpty();
        size--;
        Item result = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {

        Node current;

        public DequeIterator() {
            current = new Node();
            current.next = first;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.item;
        }
    }

    private void throwIfNull(Item item) {
        if (item == null) {
            throw  new IllegalArgumentException();
        }
    }

    private void throwIfEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // unit testing (optional)
    /*
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileInputStream(new File("/Users/iulian.manda/IdeaProjects/percolation/resources/permutation/permutation4.txt")));
        permutation.Deque<String> deque = new permutation.Deque<>();
        permutation.Deque<String> deque2 = new permutation.Deque<>();
//        while (sc.hasNext()) {
//            String item = sc.next();
//            deque.addLast(item);
//            deque2.addFirst(item);
//        }
        sc.close();
        for (String item : deque) {
            System.out.print(item + " ");
        }
        System.out.println();
        for (String item : deque2) {
            System.out.print(item + " ");
        }
        System.out.println();
        while (!deque.isEmpty()) {
            System.out.print(deque.removeLast() + " ");
        }
        System.out.println();
        while (!deque2.isEmpty()) {
            System.out.print(deque2.removeFirst() + " ");
        }
        System.out.println();
    }
    */
}