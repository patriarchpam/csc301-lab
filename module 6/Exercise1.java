/**
 * Exercise 1: Degenerate (Skewed) BST from Sorted Input Sequence.
 * 
 * Objective:
 * Insert the values 10, 20, 30, 40, 50 (already sorted) into a fresh BST,
 * one at a time, and print the resulting in-order traversal. Then describe,
 * in one or two sentences, what SHAPE this tree has and why every insertion
 * in this particular sequence costs O(n) rather than O(log n).
 * 
 * Tree Shape & Complexity Explanation:
 * Because the input sequence (10, 20, 30, 40, 50) is strictly increasing,
 * every new value is larger than all previously inserted nodes, forming a
 * completely right-skewed (degenerate) tree shaped like a singly linked list
 * (10 -> right: 20 -> right: 30 -> right: 40 -> right: 50) with no left
 * children at any node. Consequently, the tree's height h equals n - 1 rather
 * than log2(n), forcing each insertion to traverse the entire chain of existing
 * nodes from root to leaf in O(n) time instead of O(log n).
 */
public class Exercise1 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static Node root;

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.data) {
            node.left = insert(node.left, value);
        } else if (value > node.data) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    static void inorder(Node node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        sb.append(node.data).append(" ");
        inorder(node.right, sb);
    }

    static int height(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public static void main(String[] args) {
        int[] sortedValues = {10, 20, 30, 40, 50};

        for (int v : sortedValues) {
            root = insert(root, v);
        }

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);

        System.out.print("Insertion order:    ");
        for (int v : sortedValues) System.out.print(v + " ");
        System.out.println();
        System.out.println("In-order traversal: " + sb.toString().trim());
        System.out.println("Tree height:        " + height(root) + " (for n = " + sortedValues.length + " nodes)");
        System.out.println("\nShape & Complexity Analysis:");
        System.out.println("1. Shape: The tree degenerates into a right-skewed chain (linked list) where every");
        System.out.println("   node has only a right child and no left child: 10 -> 20 -> 30 -> 40 -> 50.");
        System.out.println("2. Why O(n) per insertion: Because no node branches left, half of the search space");
        System.out.println("   is never eliminated; inserting the k-th element must visit all k-1 existing nodes.");
    }
}

