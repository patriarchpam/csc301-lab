import java.util.HashMap;
import java.util.Map;

/**
 * Exercise 1: Single-Pass O(n) Balance Factor Calculation via Post-Order Traversal.
 * 
 * Objective:
 * The printBalanceFactors() method in Problem 1 recomputes height() from scratch
 * for every node, making it O(n^2) overall. Rewrite it so that each node's height
 * is computed ONCE via a single post-order pass (computing and returning a
 * subtree's height as you go), reducing the total cost to O(n). Verify the
 * rewrite reports identical balance factors to the original.
 * 
 * Complexity Analysis:
 * - Time: O(n) — each node is visited once during the post-order pass to compute
 *   heights/balance factors, and once during the in-order display pass.
 * - Space: O(h) for recursion call stack (or O(n) if caching in a map/node field).
 */
public class Exercise1 {

    static class Node {
        int data;
        int cachedHeight; // Computed once during the single post-order pass
        int cachedBF;     // Computed once during the single post-order pass
        Node left, right;
        Node(int data) { this.data = data; }
    }

    static boolean treeIsBalanced = true;

    // Single post-order pass: computes left height, right height, stores node's
    // height and BF, and returns this node's height in O(1) work per node -> O(n) total!
    static int computeHeightsAndBFPostOrder(Node node) {
        if (node == null) return -1; // height of empty subtree is -1

        int leftHeight = computeHeightsAndBFPostOrder(node.left);
        int rightHeight = computeHeightsAndBFPostOrder(node.right);

        node.cachedHeight = 1 + Math.max(leftHeight, rightHeight);
        node.cachedBF = leftHeight - rightHeight;

        if (Math.abs(node.cachedBF) > 1) {
            treeIsBalanced = false;
        }

        return node.cachedHeight;
    }

    // Prints the precomputed height and BF in in-order sequence (matching Problem 1 output)
    static void printBalanceFactorsOptimized(Node node) {
        if (node == null) return;
        printBalanceFactorsOptimized(node.left);
        System.out.println("  Node " + node.data + ": height="
                + node.cachedHeight + ", BF=" + node.cachedBF);
        printBalanceFactorsOptimized(node.right);
    }

    // Original O(n^2) methods from Problem 1 for side-by-side verification
    static int naiveHeight(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(naiveHeight(node.left), naiveHeight(node.right));
    }

    static int naiveBalanceFactor(Node node) {
        if (node == null) return 0;
        return naiveHeight(node.left) - naiveHeight(node.right);
    }

    static void printBalanceFactorsNaive(Node node) {
        if (node == null) return;
        printBalanceFactorsNaive(node.left);
        System.out.println("  Node " + node.data + ": height="
                + naiveHeight(node) + ", BF=" + naiveBalanceFactor(node));
        printBalanceFactorsNaive(node.right);
    }

    public static void main(String[] args) {
        Node root = new Node(50);
        root.left = new Node(30);
        root.right = new Node(70);
        root.left.left = new Node(20);
        root.left.right = new Node(40);
        root.right.right = new Node(80);

        // Execute the single O(n) post-order pass
        treeIsBalanced = true;
        computeHeightsAndBFPostOrder(root);

        System.out.println("=== Optimized O(n) Post-Order Pass Results (in-order display) ===");
        printBalanceFactorsOptimized(root);
        System.out.println("Is the tree height-balanced? " + treeIsBalanced);

        System.out.println("\n=== Original O(n^2) Naive Results (Verification) ===");
        printBalanceFactorsNaive(root);
    }
}

