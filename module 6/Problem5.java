/**
 * Problem 5: Validate Whether a Given Binary Tree Is a Valid BST.
 * 
 * Objective:
 * Determine whether an arbitrary binary tree satisfies the BST ordering
 * invariant at EVERY node, not merely between each node and its immediate
 * children, by propagating a valid (min, max) range down the tree.
 * 
 * Complexity:
 * - Time: O(n) — every node is visited at most once.
 * - Space: O(h) for the recursion call stack.
 */
public class Problem5 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // Recursively checks that every node's value falls within the
    // (min, max) range allowed by ALL of its ancestors so far -
    // not just its immediate parent.
    static boolean isValidBST(Node node, Integer min, Integer max) {
        if (node == null) return true;   // an empty tree is valid

        if ((min != null && node.data <= min) ||
            (max != null && node.data >= max)) {
            return false;    // violates a range set by some ANCESTOR
        }

        return isValidBST(node.left, min, node.data) &&
               isValidBST(node.right, node.data, max);
    }

    public static void main(String[] args) {
        // INVALID tree, built manually (bypassing normal insert()):
        //         10
        //        /  \
        //       5    15
        //           /  \
        //          6    20      <-- 6 is < 10 but placed in the RIGHT subtree
        Node invalidRoot = new Node(10);
        invalidRoot.left = new Node(5);
        invalidRoot.right = new Node(15);
        invalidRoot.right.left = new Node(6);
        invalidRoot.right.right = new Node(20);

        System.out.println("Invalid tree is a valid BST? " +
                            isValidBST(invalidRoot, null, null));

        // VALID tree for comparison:
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

        System.out.println("Valid tree is a valid BST?   " +
                            isValidBST(validRoot, null, null));
    }
}

