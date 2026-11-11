/**
 * Exercise 5: Step-by-Step Trace of Red-Black Tree Insertions (10, 20, 30, 15, 25, 5, 1).
 * 
 * Objective:
 * Continue the insertion sequence by hand for the remaining values (25, 5, 1)
 * using the Red-Black insertion algorithm from Problem 5, predicting at each
 * step whether the fix-up triggers a recolour, a rotation, or nothing at all.
 * Then run the program to confirm the predictions against the final in-order
 * (value+colour) output.
 * 
 * Hand-Trace Predictions for All 7 Insertions:
 * - insert(10): Inserted as root -> forced BLACK. Tree: 10(B).
 * - insert(20): Inserted as right child of 10(B) -> parent is BLACK -> NOTHING.
 * - insert(30): Inserted as right child of 20(R) -> parent 20 is RED, uncle is NIL(B)
 *   in a RR line -> SINGLE LEFT ROTATION at 10 + recolor 20(B), 10(R). Root becomes 20(B).
 * - insert(15): Inserted as right child of 10(R) -> parent 10 is RED, uncle 30 is RED
 *   -> RECOLOR ONLY (10->B, 30->B, gp 20->R, then root 20 forced B). No rotation!
 * - insert(25): Inserted as left child of 30(B) -> parent 30 is BLACK -> NOTHING!
 *   (No recolor, no rotation; 25 stays RED under 30(B)).
 * - insert(5):  Inserted as left child of 10(B) -> parent 10 is BLACK -> NOTHING!
 *   (5 stays RED, while sibling 15 is also RED under 10(B)).
 * - insert(1):  Inserted as left child of 5(R) -> parent 5 is RED, and uncle 15 is RED!
 *   -> RECOLOR ONLY: parent 5 becomes BLACK, uncle 15 becomes BLACK, grandparent 10
 *   becomes RED. Then z moves to 10(R), whose parent is root 20(B) -> loop terminates!
 *   No rotation is needed.
 */
public class Exercise5 {

    static final boolean RED = true;
    static final boolean BLACK = false;

    static class Node {
        int data;
        boolean color;
        Node left, right, parent;
        Node(int data) { this.data = data; this.color = RED; }
    }

    static Node root;
    static StringBuilder stepLog = new StringBuilder();

    static void leftRotate(Node x) {
        stepLog.append("leftRotate(").append(x.data).append("); ");
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
        stepLog.append("rightRotate(").append(x.data).append("); ");
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
        stepLog.setLength(0);
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
        if (stepLog.length() == 0) {
            stepLog.append("None (parent is BLACK or node is root)");
        }
    }

    static void fixup(Node z) {
        while (z.parent != null && z.parent.color == RED) {
            Node gp = z.parent.parent;
            if (z.parent == gp.left) {
                Node uncle = gp.right;
                if (colorOf(uncle) == RED) {
                    stepLog.append("Recolor(parent=").append(z.parent.data)
                           .append("->B, uncle=").append(uncle.data)
                           .append("->B, gp=").append(gp.data).append("->R); ");
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    gp.color = RED;
                    z = gp;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    stepLog.append("Recolor(parent=").append(z.parent.data)
                           .append("->B, gp=").append(z.parent.parent.data).append("->R); ");
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node uncle = gp.left;
                if (colorOf(uncle) == RED) {
                    stepLog.append("Recolor(parent=").append(z.parent.data)
                           .append("->B, uncle=").append(uncle.data)
                           .append("->B, gp=").append(gp.data).append("->R); ");
                    z.parent.color = BLACK;
                    uncle.color = BLACK;
                    gp.color = RED;
                    z = gp;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    stepLog.append("Recolor(parent=").append(z.parent.data)
                           .append("->B, gp=").append(z.parent.parent.data).append("->R); ");
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    static void inorder(Node n, StringBuilder sb) {
        if (n == null) return;
        inorder(n.left, sb);
        sb.append(n.data).append(n.color == RED ? "(R) " : "(B) ");
        inorder(n.right, sb);
    }

    public static void main(String[] args) {
        int[] values = {10, 20, 30, 15, 25, 5, 1};

        System.out.println("=== Step-by-Step Red-Black Insertion Trace ===");
        for (int v : values) {
            insert(v);
            StringBuilder currentInorder = new StringBuilder();
            inorder(root, currentInorder);
            System.out.printf("insert(%2d) -> Fix-up action: %-62s | In-order: %s%n",
                    v, stepLog.toString(), currentInorder.toString().trim());
        }

        System.out.println("\n=== Final Verification Output ===");
        System.out.println("Root: " + root.data
                           + (root.color == RED ? "(R)" : "(B)"));

        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        System.out.println("In-order (value+color): " + sb.toString().trim());

        System.out.println("\n=== Summary of Predictions for Remaining Values (25, 5, 1) ===");
        System.out.println("- insert(25): Parent is 30(B) -> Nothing triggered (25 stays RED).");
        System.out.println("- insert(5):  Parent is 10(B) -> Nothing triggered (5 stays RED).");
        System.out.println("- insert(1):  Parent is 5(R) and Uncle is 15(R) -> Recolor only (5->B, 15->B, 10->R); no rotation.");
    }
}

