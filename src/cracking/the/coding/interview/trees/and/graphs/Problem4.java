package cracking.the.coding.interview.trees.and.graphs;

public class Problem4 {

    static int checkHeight(Node root) {
        if (root == null) return 0;
        int left = checkHeight(root.left);
        int right = checkHeight(root.right);
        if (Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    static boolean isBalanced(Node root) {
        return checkHeight(root) != -1;
    }

    public static void main(String[] args) {
        Node<Integer> root = new Node<>(0);
        root.left = new Node<>(1);
        root.right = new Node<>(2);
        root.left.left = new Node<>(3);
        root.left.right = new Node<>(5);
        root.left.right.left = new Node<>(6);
        root.right.right = new Node<>(4);
        root.right.left = new Node<>(7);
        root.right.right.left = new Node<>(5);
        root.right.right.right = new Node<>(6);
        System.out.println(isBalanced(root));
    }

}
