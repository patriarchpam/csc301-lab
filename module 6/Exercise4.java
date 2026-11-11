import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 4: Lowest Common Ancestor (LCA) With Root-to-Target Path Tracing.
 * 
 * Objective:
 * Extend Problem 4 so that it also PRINTS the full path from the root to each
 * of the two target values (not just the LCA itself). Use this to explain,
 * referencing the printed paths, exactly why 30 (and not 50) is the correct
 * LCA for the pair (20, 40).
 * 
 * Explanation Referencing Printed Paths:
 * - Path from root to 20: [50 -> 30 -> 20]
 * - Path from root to 40: [50 -> 30 -> 40]
 * Both 50 and 30 appear in both paths, making both of them common ancestors of
 * 20 and 40. However, the Lowest Common Ancestor (LCA) is defined as the
 * DEEPEST node (farthest from the root) where the two paths diverge. Since
 * both paths move left from 50 to 30 and only diverge at 30 (20 goes left,
 * 40 goes right), 30 is the deepest common ancestor and therefore the LCA.
 */
public class Exercise4 {

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

        if (a < node.data && b < node.data) {
            return findLCA(node.left, a, b);
        }
        if (a > node.data && b > node.data) {
            return findLCA(node.right, a, b);
        }
        return node;
    }

    // Traces and returns the path of node values from the root down to target
    static List<Integer> getPathToNode(Node node, int target) {
        List<Integer> path = new ArrayList<>();
        Node curr = node;
        while (curr != null) {
            path.add(curr.data);
            if (target == curr.data) {
                break;
            }
            curr = (target < curr.data) ? curr.left : curr.right;
        }
        return path;
    }

    // Helper to format a path list with arrows
    static String formatPath(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }

    static void analyzeLCAWithPaths(int a, int b) {
        List<Integer> pathA = getPathToNode(root, a);
        List<Integer> pathB = getPathToNode(root, b);
        Node lca = findLCA(root, a, b);

        System.out.println("Query: LCA(" + a + ", " + b + ")");
        System.out.println("  Path to " + a + ": " + formatPath(pathA));
        System.out.println("  Path to " + b + ": " + formatPath(pathB));
        System.out.println("  LCA(" + a + ", " + b + ") = " + lca.data);
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) root = insert(root, v);

        analyzeLCAWithPaths(20, 60);
        System.out.println();
        analyzeLCAWithPaths(20, 40);

        System.out.println("\n=== Why 30 (and not 50) is the LCA for (20, 40) ===");
        System.out.println("1. Path to 20 is [50 -> 30 -> 20] and Path to 40 is [50 -> 30 -> 40].");
        System.out.println("2. Both 50 and 30 are common ancestors because both appear on the shared prefix [50 -> 30].");
        System.out.println("3. At node 50, both 20 and 40 are smaller than 50, so both paths continue together to 30.");
        System.out.println("4. Node 30 is the deepest node where the paths diverge (20 < 30 goes left; 40 > 30 goes right),");
        System.out.println("   making 30 the LOWEST Common Ancestor.");
    }
}

