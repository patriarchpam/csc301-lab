/**
 * Problem 3: Delete a Node From a BST While Maintaining BST Properties.
 * 
 * Objective:
 * Remove a specified value from a BST while preserving the BST invariant for
 * every remaining node, handling all three structural cases:
 * - Case 1: Leaf node (no children)
 * - Case 2: Node with one child
 * - Case 3: Node with two children (replaced by its inorder successor)
 * 
 * Complexity:
 * - Time: O(h) where h is tree height.
 * - Space: O(h) for the recursion call stack.
 */
public class Problem3 {

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

    static Node delete(Node node, int value) {
        if (node == null) return null;   // value not found

        if (value < node.data) {
            node.left = delete(node.left, value);
        } else if (value > node.data) {
            node.right = delete(node.right, value);
        } else {
            // FOUND the node to delete - three cases:

            // Case 1: no children (leaf) - simply remove it
            if (node.left == null && node.right == null) {
                return null;
            }
            // Case 2: exactly one child - replace node with that child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Case 3: two children - replace with the INORDER SUCCESSOR
            // (the smallest value in the right subtree), then delete
            // that successor from its original position.
            Node successor = findMin(node.right);
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }
        return node;
    }

    static Node findMin(Node node) {
        while (node.left != null) node = node.left;
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
        for (int v : values) root = insert(root, v);

        StringBuilder before = new StringBuilder();
        inorder(root, before);
        System.out.println("Before delete(30): " + before.toString().trim());

        root = delete(root, 30);   // two-children case

        StringBuilder after = new StringBuilder();
        inorder(root, after);
        System.out.println("After  delete(30): " + after.toString().trim());
    }
}

