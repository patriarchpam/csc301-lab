/**
 * Problem 2: Search for a Given Value in a BST.
 * 
 * Objective:
 * Determine whether a given value exists in a BST, using the ordering invariant
 * to eliminate half of the remaining tree at every step, rather than checking
 * every node.
 * 
 * Complexity:
 * - Time: O(h) where h is tree height (O(log n) balanced, O(n) worst case).
 * - Space: O(1) for the iterative search.
 */
public class Problem2 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static Node root;
    static int comparisons;

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.data) node.left = insert(node.left, value);
        else if (value > node.data) node.right = insert(node.right, value);
        return node;
    }

    static boolean search(Node node, int target) {
        comparisons = 0;
        while (node != null) {
            comparisons++;
            if (target == node.data) return true;
            node = (target < node.data) ? node.left : node.right;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) root = insert(root, v);

        boolean found = search(root, 60);
        System.out.println("search(60) -> " + found +
                            " (comparisons: " + comparisons + ")");

        boolean notFound = search(root, 65);
        System.out.println("search(65) -> " + notFound +
                            " (comparisons: " + comparisons + ")");
    }
}

