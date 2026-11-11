/**
 * Exercise 3: Testing All Three BST Deletion Cases (Leaf, One-Child, Two-Children).
 * 
 * Objective:
 * Test the BST deletion program by deleting:
 * 1. A LEAF node (e.g., 20) — triggers Case 1 (no children).
 * 2. A node with ONE child (e.g., delete 70 after first deleting 80, leaving 70
 *    with only its left child 60) — triggers Case 2 (one child).
 * 3. A node with TWO children (e.g., 30) — triggers Case 3 (inorder successor).
 * For each case, print the in-order traversal before and after, and identify
 * which deletion case was triggered.
 */
public class Exercise3 {

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
        if (node == null) return null;

        if (value < node.data) {
            node.left = delete(node.left, value);
        } else if (value > node.data) {
            node.right = delete(node.right, value);
        } else {
            // Case 1: Leaf node (no children) -> return null to detach from parent
            if (node.left == null && node.right == null) {
                System.out.println("  [Triggered Case 1: Leaf node (" + value + ") has 0 children -> detached]");
                return null;
            }
            // Case 2: Exactly one child -> bypass node and return its single child
            if (node.left == null) {
                System.out.println("  [Triggered Case 2: Node (" + value + ") has 1 right child (" + node.right.data + ") -> bypassed]");
                return node.right;
            }
            if (node.right == null) {
                System.out.println("  [Triggered Case 2: Node (" + value + ") has 1 left child (" + node.left.data + ") -> bypassed]");
                return node.left;
            }

            // Case 3: Two children -> replace with inorder successor, then delete successor
            Node successor = findMin(node.right);
            System.out.println("  [Triggered Case 3: Node (" + value + ") has 2 children -> replaced by inorder successor (" + successor.data + ")]");
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }
        return node;
    }

    static Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    static String getInorder(Node node) {
        StringBuilder sb = new StringBuilder();
        inorderHelper(node, sb);
        return sb.toString().trim();
    }

    static void inorderHelper(Node node, StringBuilder sb) {
        if (node == null) return;
        inorderHelper(node.left, sb);
        sb.append(node.data).append(" ");
        inorderHelper(node.right, sb);
    }

    public static void main(String[] args) {
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) root = insert(root, v);

        // --- Test 1: Delete a LEAF node (20) ---
        // Node 20 has left == null and right == null, triggering Case 1 (no children).
        System.out.println("=== Test 1: Deleting Leaf Node (20) ===");
        System.out.println("Before delete(20): " + getInorder(root));
        root = delete(root, 20);
        System.out.println("After  delete(20): " + getInorder(root));

        // --- Test 2: Delete a node with ONE child (70 after deleting leaf 80) ---
        // First delete leaf 80 (Case 1) so that 70 has only a left child (60).
        // Then deleting 70 triggers Case 2 (one child: node.right == null, returns node.left = 60).
        System.out.println("\n=== Test 2: Deleting One-Child Node (70, after removing leaf 80) ===");
        System.out.println("Step 2a - Removing leaf 80 first so 70 has only one child (60):");
        root = delete(root, 80);
        System.out.println("Before delete(70): " + getInorder(root));
        root = delete(root, 70);
        System.out.println("After  delete(70): " + getInorder(root));

        // --- Test 3: Delete a node with TWO children (50, the root) ---
        // Node 50 has both left subtree (30, 40) and right subtree (60), triggering Case 3.
        System.out.println("\n=== Test 3: Deleting Two-Children Node (50) ===");
        System.out.println("Before delete(50): " + getInorder(root));
        root = delete(root, 50);
        System.out.println("After  delete(50): " + getInorder(root));
    }
}

