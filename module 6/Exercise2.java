/**
 * Exercise 2: Recursive BST Search With Comparison Counter.
 * 
 * Objective:
 * Rewrite search() as a recursive method instead of an iterative loop. Then add
 * a countNodesVisited feature to the recursive version and confirm it reports
 * the same comparison counts as the iterative version for both search(60) and
 * search(65).
 * 
 * Complexity:
 * - Time: O(h) where h is tree height (O(log n) balanced, O(n) worst case).
 * - Space: O(h) for the recursion call stack (vs O(1) for the iterative version).
 */
public class Exercise2 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static Node root;
    static int countNodesVisited;
    static int iterativeComparisons;

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.data) node.left = insert(node.left, value);
        else if (value > node.data) node.right = insert(node.right, value);
        return node;
    }

    // Public wrapper that resets countNodesVisited before starting recursive search
    static boolean searchRecursive(Node node, int target) {
        countNodesVisited = 0;
        return searchRecursiveHelper(node, target);
    }

    // Recursive BST search helper
    static boolean searchRecursiveHelper(Node node, int target) {
        if (node == null) {
            return false; // Base case: reached empty subtree without finding target
        }

        countNodesVisited++; // Increment count for visiting and comparing this node

        if (target == node.data) {
            return true;  // Target found
        } else if (target < node.data) {
            return searchRecursiveHelper(node.left, target);  // Search left subtree
        } else {
            return searchRecursiveHelper(node.right, target); // Search right subtree
        }
    }

    // Original iterative search for comparison verification
    static boolean searchIterative(Node node, int target) {
        iterativeComparisons = 0;
        while (node != null) {
            iterativeComparisons++;
            if (target == node.data) return true;
            node = (target < node.data) ? node.left : node.right;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) root = insert(root, v);

        // Test 1: search(60)
        boolean recFound60 = searchRecursive(root, 60);
        boolean iterFound60 = searchIterative(root, 60);
        System.out.println("Recursive search(60) -> " + recFound60 +
                           " (countNodesVisited: " + countNodesVisited + ")");
        System.out.println("Iterative search(60) -> " + iterFound60 +
                           " (comparisons:       " + iterativeComparisons + ")");

        System.out.println();

        // Test 2: search(65)
        boolean recFound65 = searchRecursive(root, 65);
        boolean iterFound65 = searchIterative(root, 65);
        System.out.println("Recursive search(65) -> " + recFound65 +
                           " (countNodesVisited: " + countNodesVisited + ")");
        System.out.println("Iterative search(65) -> " + iterFound65 +
                           " (comparisons:       " + iterativeComparisons + ")");
    }
}

