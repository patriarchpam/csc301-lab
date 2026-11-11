import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 5: BST Validation via In-Order Traversal List vs. Range-Based Method.
 * 
 * Objective:
 * Write a second validation method that performs a full in-order traversal into
 * a list and then checks that the list is strictly increasing. Run it on both
 * trees from Problem 5 to confirm it agrees with the range-based method, then
 * state one advantage the range-based method has over this traversal-based
 * alternative.
 * 
 * Advantage of the Range-Based Method over the Traversal-into-List Method:
 * 1. Space Efficiency (O(h) vs O(n)): The range-based method uses only O(h)
 *    stack space (O(log n) for a balanced tree) and allocates no extra collection,
 *    whereas the list-based traversal must allocate an O(n) List to store all n
 *    elements before checking.
 * 2. Early Termination (Short-Circuiting): The range-based method immediately
 *    returns `false` the moment it encounters the first offending node, whereas
 *    building the full in-order list visits all n nodes before checking order.
 */
public class Exercise5 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // Method 1: Range-based validation from Problem 5
    static boolean isValidBSTRange(Node node, Integer min, Integer max) {
        if (node == null) return true;

        if ((min != null && node.data <= min) ||
            (max != null && node.data >= max)) {
            return false;
        }

        return isValidBSTRange(node.left, min, node.data) &&
               isValidBSTRange(node.right, node.data, max);
    }

    // Method 2: In-order traversal into a List, checking strictly increasing order
    static boolean isValidBSTInorderList(Node root) {
        List<Integer> values = new ArrayList<>();
        collectInorder(root, values);

        // Verify that every element is strictly greater than its predecessor
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) <= values.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    static void collectInorder(Node node, List<Integer> list) {
        if (node == null) return;
        collectInorder(node.left, list);
        list.add(node.data);
        collectInorder(node.right, list);
    }

    public static void main(String[] args) {
        // INVALID tree from Problem 5:
        //         10
        //        /  \
        //       5    15
        //           /  \
        //          6    20      <-- 6 < 10 in right subtree
        Node invalidRoot = new Node(10);
        invalidRoot.left = new Node(5);
        invalidRoot.right = new Node(15);
        invalidRoot.right.left = new Node(6);
        invalidRoot.right.right = new Node(20);

        // VALID tree from Problem 5:
        //         10
        //        /  \
        //       5    15
        //           /  \
        //          12   20
        Node validRoot = new Node(10);
        validRoot.left = new Node(5);
        validRoot.right = new Node(15);
        validRoot.right.left = new Node(12);
        validRoot.right.right = new Node(20);

        List<Integer> invalidList = new ArrayList<>();
        collectInorder(invalidRoot, invalidList);
        System.out.println("Invalid tree in-order list: " + invalidList + " (not strictly increasing: 10 -> 6)");
        System.out.println("  Range-based isValidBSTRange:       " + isValidBSTRange(invalidRoot, null, null));
        System.out.println("  List-based  isValidBSTInorderList: " + isValidBSTInorderList(invalidRoot));

        System.out.println();

        List<Integer> validList = new ArrayList<>();
        collectInorder(validRoot, validList);
        System.out.println("Valid tree in-order list:   " + validList + " (strictly increasing)");
        System.out.println("  Range-based isValidBSTRange:       " + isValidBSTRange(validRoot, null, null));
        System.out.println("  List-based  isValidBSTInorderList: " + isValidBSTInorderList(validRoot));

        System.out.println("\n=== Advantage of Range-Based Method Over In-Order List Method ===");
        System.out.println("1. Space Complexity: Range-based validation uses only O(h) recursion stack space,");
        System.out.println("   whereas the list-based method requires O(n) extra heap memory to store all n node values.");
        System.out.println("2. Early Termination: Range-based validation stops and returns false immediately upon");
        System.out.println("   visiting a violating node, whereas the list method traverses the entire tree first.");
    }
}

