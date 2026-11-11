/**
 * Problem 4: Find the Lowest Common Ancestor (LCA) of Two Nodes in a BST.
 * 
 * Objective:
 * Find the deepest node in a BST that has both of two given values as
 * descendants (a node is considered its own descendant for this purpose).
 * 
 * Complexity:
 * - Time: O(h) — single downward pass from the root.
 * - Space: O(h) for recursion call stack (or O(1) if iterative).
 */
public class Problem4 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static Node root;

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.data) node.left = insert(node.left, value);
        else if (value > node.data) node.right = insert(node.right, value);
        return node;
    }

    static Node findLCA(Node node, int a, int b) {
        if (node == null) return null;

        // if both values are smaller, the LCA must be in the left subtree
        if (a < node.data && b < node.data) {
            return findLCA(node.left, a, b);
        }
        // if both values are larger, the LCA must be in the right subtree
        if (a > node.data && b > node.data) {
            return findLCA(node.right, a, b);
        }
        // otherwise paths diverge HERE (or one equals node.data)
        return node;
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) root = insert(root, v);

        Node result1 = findLCA(root, 20, 60);
        System.out.println("LCA(20, 60) = " + result1.data);

        Node result2 = findLCA(root, 20, 40);
        System.out.println("LCA(20, 40) = " + result2.data);
    }
}

