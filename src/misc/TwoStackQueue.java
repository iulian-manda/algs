package misc;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TwoStackQueue<Item> {
    private Stack<Item> enqueue;
    private Stack<Item> dequeue;

    public TwoStackQueue() {
        enqueue = new Stack<>();
        dequeue = new Stack<>();
    }

    public void enqueue(Item item) {
        moveItems(dequeue, enqueue);
        enqueue.push(item);
    }

    public Item dequeue() {
        moveItems(enqueue, dequeue);
        return dequeue.pop();
    }

    public boolean isEmpty() {
        return enqueue.isEmpty() && dequeue.isEmpty();
    }

    private void moveItems(Stack<Item> source, Stack<Item> destination) {
        while(!source.isEmpty()) {
            destination.push(source.pop());
        }
    }

    public static void main(String[] args) {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();
        Queue<Integer> queue2 = new ConcurrentLinkedQueue<>();
        double start = System.currentTimeMillis();
        for(int i = 1; i <= 10000000; i++) {
            queue.enqueue(i);
            if(i % 1000000 == 0) {
                while(!queue.isEmpty()) {
                    queue.dequeue();
                }
            }
        }
        double end = System.currentTimeMillis();
        System.out.println("Custom queue: " + (end - start));
        start = System.currentTimeMillis();
        for(int i = 1; i <= 10000000; i++) {
            queue2.add(i);
            if(i % 1000000 == 0) {
                while(!queue2.isEmpty()) {
                    queue2.remove();
                }
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Queue: " + (end - start));
    }
}