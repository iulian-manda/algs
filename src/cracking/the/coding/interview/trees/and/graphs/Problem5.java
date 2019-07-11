package cracking.the.coding.interview.trees.and.graphs;

public class Problem5 {

public static boolean checkBST(Node<Integer> root) {
    return checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
}

private static boolean checkBST(Node<Integer> node, int min, int max) {
    if (node == null) return true;
    if (node.value < min || node.value >= max) return false;
    return checkBST(node.left, min, node.value) && checkBST(node.right, node.value, max);
}

    public static void main(String[] args) {
        Node<Integer> root = new Node<>(8);
        root.left = new Node<>(4);
        root.left.left = new Node<>(2);
        root.left.right = new Node<>(12);
        root.right = new Node<>(10);
        root.right.right = new Node<>(20);
        System.out.println(checkBST(root));
    }

}
