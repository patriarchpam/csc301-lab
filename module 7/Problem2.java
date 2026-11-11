/**
 * Problem 2: Perform a Single Left Rotation and Single Right Rotation on an Unbalanced BST.
 * 
 * Objective:
 * Implement the two fundamental tree-restructuring operations, left rotation
 * and right rotation, and use them to fix the two simplest AVL imbalance cases:
 * LL (left-left) and RR (right-right).
 * 
 * Complexity:
 * - Time: O(1) per rotation — fixed number of pointer reassignments.
 * - Space: O(1) — restructures existing nodes in place.
 */
public class Problem2 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // Single RIGHT rotation - fixes an LL (left-left) imbalance
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;     // y becomes x's right child
        y.left = T2;     // y adopts x's old right subtree

        return x;        // x is the new subtree root
    }

    // Single LEFT rotation - fixes an RR (right-right) imbalance
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;      // x becomes y's left child
        x.right = T2;    // x adopts y's old left subtree

        return y;        // y is the new subtree root
    }

    static void printPreorder(Node node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(" ");
        printPreorder(node.left, sb);
        printPreorder(node.right, sb);
    }

    public static void main(String[] args) {
        // ---- LL case: chain 30 -> 20 -> 10 ----
        Node llRoot = new Node(30);
        llRoot.left = new Node(20);
        llRoot.left.left = new Node(10);

        StringBuilder before1 = new StringBuilder();
        printPreorder(llRoot, before1);
        System.out.println("LL before: " + before1.toString().trim());

        llRoot = rightRotate(llRoot);

        StringBuilder after1 = new StringBuilder();
        printPreorder(llRoot, after1);
        System.out.println("LL after right-rotate: "
                           + after1.toString().trim());

        // ---- RR case: chain 10 -> 20 -> 30 ----
        Node rrRoot = new Node(10);
        rrRoot.right = new Node(20);
        rrRoot.right.right = new Node(30);

        StringBuilder before2 = new StringBuilder();
        printPreorder(rrRoot, before2);
        System.out.println("RR before: " + before2.toString().trim());

        rrRoot = leftRotate(rrRoot);

        StringBuilder after2 = new StringBuilder();
        printPreorder(rrRoot, after2);
        System.out.println("RR after left-rotate: "
                           + after2.toString().trim());
    }
}

