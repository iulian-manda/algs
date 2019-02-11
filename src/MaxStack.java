public class MaxStack<Item extends Comparable> {

    Node first;
    Item max;

    public void push(Item item) {
        Node oldFirst = first;
        first = new Node();
        if (max == null) {
            max = item;
        } else if (item.compareTo(max) > 0) {
            max = item;
        }
        first.item = item;
        first.next = oldFirst;
    }

    public Item pop() {
        Item result = first.item;
        first = first.next;
        if(result == max) {
            findNextMax();
        }
        return result;
    }

    public Item getMax() {
        return max;
    }

    public boolean isEmpty() {
        return first == null;
    }

    private void findNextMax(){
        if(first == null) {
            max = null; return;
        }
        Node current = first;
        max = current.item;
        while(current.next != null) {
            current = current.next;
            if(current.item.compareTo(max) > 0) max = current.item;
        }
    }

    private class Node {
        Item item;
        Node next;
    }

    public static void main(String[] args) {
        MaxStack<Integer> stack = new MaxStack<>();
        int[] numbers = {1, 6, 2, 3, 9, 15, 16, 17, 18, -3, 5};
        for(int n : numbers) {
            stack.push(n);
        }
        System.out.println(stack.getMax());
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
            System.out.println("m " + stack.getMax());
        }
    }
}