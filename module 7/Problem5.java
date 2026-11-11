/**
 * Problem 5: Implement Red-Black Tree Insertion With Proper Recoloring and Rotations.
 * 
 * Objective:
 * Implement insertion into a Red-Black tree, maintaining its five color-based
 * invariants using a combination of recoloring (when the uncle is RED) and
 * rotation (when the uncle is BLACK).
 * 
 * Complexity:
 * - Time: O(log n) per insertion (at most O(log n) recolorings and at most 2 rotations).
 * - Space: O(n) total nodes.
 */
public class Problem5 {

    static final boolean RED = true;
    static final boolean BLACK = false;

    static class Node {
        int data;
        boolean color;
        Node left, right, parent;
        Node(int data) { this.data = data; this.color = RED; }
    }

    static Node root;

    static void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    static void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.right) x.parent.right = y;
        else x.parent.left = y;
        y.right = x;
        x.parent = y;
    }

    static boolean colorOf(Node n) { return (n == null) ? BLACK : n.color; }

    static void insert(int value) {
        Node z = new Node(value);
        Node y = null;
        Node x = root;

        while (x != null) {
            y = x;
            x = (z.data < x.data) ? x.left : x.right;
        }
        z.parent = y;
        if (y == null) root = z;
        else if (z.data < y.data) y.left = z;
        else y.right = z;

        fixup(z);
    }

    static void fixup(Node z) {
        while (z.parent != null && z.parent.color == RED) {
            Node gp = z.parent.parent;
            if (z.parent == gp.left) {
                Node uncle = gp.right;
                if (colorOf(uncle) == RED) {
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    gp.color = RED;
                    z = gp;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node uncle = gp.left;
                if (colorOf(uncle) == RED) {
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    gp.color = RED;
                    z = gp;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;   // the root is always black
    }

    static void inorder(Node n, StringBuilder sb) {
        if (n == null) return;
        inorder(n.left, sb);
        sb.append(n.data).append(n.color == RED ? "(R) " : "(B) ");
        inorder(n.right, sb);
    }

    public static void main(String[] args) {
        int[] values = {10, 20, 30, 15, 25, 5, 1};

        for (int v : values) {
            insert(v);
        }

        System.out.println("Root: " + root.data
                           + (root.color == RED ? "(R)" : "(B)"));

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        System.out.println("In-order (value+color): " + sb.toString().trim());
    }
}

