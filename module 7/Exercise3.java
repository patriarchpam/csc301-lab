/**
 * Exercise 3: Sorted Insertion (1..7) Into an AVL Tree vs. Plain BST.
 * 
 * Objective:
 * Insert the values 1, 2, 3, 4, 5, 6, 7 (already sorted) into a fresh AVL tree,
 * one at a time, printing the root after each insertion. Compare the number of
 * rotations triggered against the plain BST from Module 6's Laboratory Exercise
 * (Problem 1), which degenerates into a straight chain for this same input,
 * and state the final tree's height.
 * 
 * Comparison Summary:
 * - Plain BST (Module 6): Performs 0 rotations and degenerates into a right-skewed
 *   chain of height 6 (h = n - 1 = 6).
 * - AVL Tree: Performs 4 single left rotations (at insertions 3, 5, 6, and 7) to
 *   produce a perfectly balanced complete binary tree (root 4, left child 2 with
 *   children 1 & 3, right child 6 with children 5 & 7) of height 2 (floor(log2(7)) = 2).
 */
public class Exercise3 {

    static class Node {
        int data, height;
        Node left, right;
        Node(int data) {
            this.data = data;
            this.height = 0;
        }
    }

    static Node root;
    static int rotationCount = 0;

    static int height(Node n) { return (n == null) ? -1 : n.height; }

    static int balanceFactor(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    static Node rightRotate(Node y) {
        rotationCount++;
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    static Node leftRotate(Node x) {
        rotationCount++;
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    static Node insert(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.data) node.left = insert(node.left, value);
        else if (value > node.data) node.right = insert(node.right, value);
        else return node;

        updateHeight(node);
        int bf = balanceFactor(node);

        // LL case
        if (bf > 1 && value < node.left.data) return rightRotate(node);
        // RR case
        if (bf < -1 && value > node.right.data) return leftRotate(node);
        // LR case
        if (bf > 1 && value > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL case
        if (bf < -1 && value < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    static void inorder(Node node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        sb.append(node.data).append(" ");
        inorder(node.right, sb);
    }

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 5, 6, 7};

        System.out.println("=== Inserting Sorted Sequence {1, 2, 3, 4, 5, 6, 7} Into AVL Tree ===");
        for (int v : values) {
            int beforeRotations = rotationCount;
            root = insert(root, v);
            int triggered = rotationCount - beforeRotations;
            System.out.println("insert(" + v + ") -> root is now "
                    + root.data + ", height=" + root.height
                    + " (rotations triggered on this step: " + triggered + ")");
        }

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        System.out.println("\nFinal in-order:           " + sb.toString().trim());
        System.out.println("Final AVL tree height:    " + root.height);
        System.out.println("Total AVL rotations:      " + rotationCount);
        System.out.println("\n=== Comparison Against Plain BST (Module 6, Exercise 1) ===");
        System.out.println("- Plain BST: Performs 0 rotations, degenerating into a right-skewed chain of height 6.");
        System.out.println("- AVL Tree:  Triggers 4 single left (RR) rotations (at 3, 5, 6, and 7), maintaining a");
        System.out.println("  perfectly balanced tree of height 2 with root = 4.");
    }
}

