package cracking.the.coding.interview.trees.and.graphs;

public class Node<T> {
    public Node left, right;
    public T value;

    public Node(T value) {
        this.value = value;
    }
}
