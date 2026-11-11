/**
 * Problem 1: Insert a Series of Values Into a BST and Print an In-Order Traversal.
 * 
 * Objective:
 * Build a Binary Search Tree by inserting a sequence of values one at a time,
 * and verify the tree's correctness by printing its contents in sorted order
 * via an in-order traversal.
 * 
 * Complexity:
 * - Time: O(h) per insertion (O(log n) balanced, O(n) worst case); O(n) for traversal.
 * - Space: O(h) recursion stack during insertion/traversal; O(n) total nodes.
 */
public class Problem1 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static Node root;

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);      // found the empty spot

        if (value < node.data) {
            node.left = insert(node.left, value);     // left subtree
        } else if (value > node.data) {
            node.right = insert(node.right, value);   // right subtree
        }
        // if value == node.data, ignore (no duplicates here)
        return node;
    }

    static void inorder(Node node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        sb.append(node.data).append(" ");
        inorder(node.right, sb);
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};

        for (int v : values) {
            root = insert(root, v);
        }

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);

        System.out.print("Insertion order: ");
        for (int v : values) System.out.print(v + " ");
        System.out.println();
        System.out.println("In-order traversal: " + sb.toString().trim());
    }
}

