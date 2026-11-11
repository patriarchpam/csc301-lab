/**
 * Exercise 4: Tracing and Verifying AVL Deletion of Node 70.
 * 
 * Objective:
 * Build the same AVL tree used in Problem 4 ({50, 30, 70, 20, 40, 60, 80, 10}),
 * then delete 70 instead of 30. Trace through the algorithm by hand first to
 * predict whether any rotation will trigger, then verify your prediction by
 * running the program with delete(70) and printing the tree's in-order result
 * and root before/after.
 * 
 * Hand-Trace Prediction for delete(70):
 * 1. Initial tree before delete(70):
 *    - Root 50 (height = 3):
 *      - Left subtree rooted at 30 (height = 2): left child 20 (with left child 10), right child 40.
 *      - Right subtree rooted at 70 (height = 1): left child 60 (height = 0), right child 80 (height = 0).
 * 2. Standard BST deletion of 70 (two children: 60 and 80):
 *    - Inorder successor of 70 is 80 (minimum of right subtree).
 *    - Node 70's value is replaced by 80, and leaf 80 is deleted.
 *    - The right child of 50 is now 80 (with left child 60 and right child null),
 *      so its height remains 1 and its balance factor becomes +1 (height(60)=0 - height(null)=-1 => 1).
 * 3. Unwinding to Root 50:
 *    - Left subtree (30) has height 2; right subtree (80) STILL has height 1!
 *    - BF(50) = 2 - 1 = +1, which is within {-1, 0, +1}.
 * 4. Prediction: NO ROTATION is triggered! Root remains 50.
 *    (Note: If a subsequent deletion removed 60 as well, 80's height would drop to 0,
 *    making BF(50) = 2 - 0 = +2 and triggering an LL right-rotation at 50.)
 */
public class Exercise4 {

    static class Node {
        int data, height;
        Node left, right;
        Node(int data) { this.data = data; this.height = 0; }
    }

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

        if (bf > 1 && value < node.left.data) return rightRotate(node);
        if (bf < -1 && value > node.right.data) return leftRotate(node);
        if (bf > 1 && value > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (bf < -1 && value < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    static Node minValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    static Node delete(Node node, int value) {
        if (node == null) return null;

        if (value < node.data) {
            node.left = delete(node.left, value);
        } else if (value > node.data) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = minValueNode(node.right);
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }

        updateHeight(node);
        int bf = balanceFactor(node);

        if (bf > 1 && balanceFactor(node.left) >= 0) return rightRotate(node);
        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (bf < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        if (bf < -1 && balanceFactor(node.right) > 0) {
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
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10};
        Node root = null;
        for (int v : values) root = insert(root, v);

        StringBuilder before = new StringBuilder();
        inorder(root, before);
        System.out.println("Before delete(70): " + before.toString().trim());
        System.out.println("Root before:       " + root.data + " (BF=" + balanceFactor(root) + ")");

        rotationCount = 0; // Reset rotation counter before delete(70)
        root = delete(root, 70);

        StringBuilder after = new StringBuilder();
        inorder(root, after);
        System.out.println("After  delete(70): " + after.toString().trim());
        System.out.println("Root after:        " + root.data + " (BF=" + balanceFactor(root) + ")");
        System.out.println("Root's right child after delete(70): " + root.right.data
                + " (height=" + root.right.height + ", BF=" + balanceFactor(root.right) + ")");
        System.out.println("Rotations triggered by delete(70):   " + rotationCount);

        System.out.println("\n=== Hand-Trace Explanation ===");
        System.out.println("1. Node 70 has two children (60 and 80), so it is replaced by its inorder successor (80).");
        System.out.println("2. The right subtree is now rooted at 80 with left child 60, so its height is STILL 1 (BF = +1).");
        System.out.println("3. At root 50, height(left=30) = 2 and height(right=80) = 1, giving BF(50) = 2 - 1 = +1.");
        System.out.println("4. Since all balance factors remain in {-1, 0, +1}, 0 rotations are triggered and root stays 50.");
    }
}

