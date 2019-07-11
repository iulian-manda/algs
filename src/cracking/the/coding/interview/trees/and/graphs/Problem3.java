package cracking.the.coding.interview.trees.and.graphs;

import java.util.LinkedList;

public class Problem3 {

    static LinkedList<Node>[] createLinkedList(Node root, int depth) {
        LinkedList<Node>[] result = new LinkedList[depth];
        createLinkedList(root, result, 0);
        return result;
    }

    static void createLinkedList(Node node, LinkedList<Node>[] list, int level) {
        if (node == null) return;
        if (list[level] == null) list[level] = new LinkedList<>();
        list[level].addLast(node);
        createLinkedList(node.left, list, level + 1);
        createLinkedList(node.right, list, level + 1);
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(0);
        node.left = new Node<>(1);
        node.right = new Node<>(2);
        LinkedList<Node>[] result = createLinkedList(node, 2);
        System.out.println(result);
    }

}
