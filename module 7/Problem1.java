/**
 * Problem 1: Explain and Calculate the Balance Factor of Nodes in a Given Binary Tree.
 * 
 * Objective:
 * Compute, for every node in a binary tree, its height and its BALANCE FACTOR
 * (height(left) - height(right)), and determine whether the tree as a whole is
 * height-balanced.
 * 
 * Complexity:
 * - Time: O(n^2) for the naive standalone height() recomputation at each node
 *   (reduced to O(n) in Exercise 1).
 * - Space: O(h) for the recursion call stack.
 */
public class Problem1 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // height(NULL) is defined as -1, so a leaf has height 0
    static int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static int balanceFactor(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    static void printBalanceFactors(Node node) {
        if (node == null) return;
        printBalanceFactors(node.left);
        System.out.println("  Node " + node.data + ": height="
                + height(node) + ", BF=" + balanceFactor(node));
        printBalanceFactors(node.right);
    }

    static boolean isHeightBalanced(Node node) {
        if (node == null) return true;
        int bf = balanceFactor(node);
        if (Math.abs(bf) > 1) return false;
        return isHeightBalanced(node.left) && isHeightBalanced(node.right);
    }

    public static void main(String[] args) {
        // Manually build the fixed example tree (no auto-balancing here)
        Node root = new Node(50);
        root.left = new Node(30);
        root.right = new Node(70);
        root.left.left = new Node(20);
        root.left.right = new Node(40);
        root.right.right = new Node(80);

        System.out.println("Balance factors (in-order):");
        printBalanceFactors(root);

        System.out.println("Is the tree height-balanced? "
                           + isHeightBalanced(root));
    }
}

