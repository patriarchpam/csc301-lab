/**
 * Problem 4: Delete a Node From an AVL Tree and Rebalance the Tree Afterward.
 * 
 * Objective:
 * Remove a specified value from an AVL tree while restoring the height-balance
 * invariant at every affected ancestor, using child balance factors to select
 * the appropriate rotation (LL, LR, RR, RL).
 * 
 * Complexity:
 * - Time: O(log n) for deletion and rebalancing along the ancestor path.
 * - Space: O(log n) for the recursion call stack.
 */
public class Problem4 {

    static class Node {
        int data, height;
        Node left, right;
        Node(int data) { this.data = data; this.height = 0; }
    }

    static int height(Node n) { return (n == null) ? -1 : n.height; }
    static int balanceFactor(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }
    static void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    static Node leftRotate(Node x) {
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
            // node found - standard BST deletion first
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = minValueNode(node.right);
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }

        updateHeight(node);
        int bf = balanceFactor(node);

        // LL / LR cases (left-heavy after deletion)
        if (bf > 1 && balanceFactor(node.left) >= 0) return rightRotate(node);
        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RR / RL cases (right-heavy after deletion)
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
        System.out.println("Before delete(30): " + before.toString().trim());
        System.out.println("Root before: " + root.data);

        root = delete(root, 30);

        StringBuilder after = new StringBuilder();
        inorder(root, after);
        System.out.println("After  delete(30): " + after.toString().trim());
        System.out.println("Root's left child after rebalance: "
                           + root.left.data);
    }
}

