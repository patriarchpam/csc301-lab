/**
 * Exercise 2: Verifying In-Order Invariance Across Left and Right Rotations.
 * 
 * Objective:
 * Extend Problem 2 to print each tree's IN-ORDER traversal before and after its
 * rotation (not just the pre-order shown in Problem 2), and confirm that the
 * in-order sequence is IDENTICAL before and after rotation in both cases.
 * Explain in one sentence why a rotation must never change the in-order
 * traversal of a BST.
 * 
 * Why a Rotation Never Changes the In-Order Traversal of a BST:
 * A rotation only restructures parent-child heights locally while strictly
 * preserving the BST ordering invariant (left < parent < right), and since the
 * in-order traversal of any valid BST visits keys in sorted ascending order,
 * the in-order sequence must remain identical before and after any rotation.
 */
public class Exercise2 {

    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // Single RIGHT rotation - fixes an LL (left-left) imbalance
    static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    // Single LEFT rotation - fixes an RR (right-right) imbalance
    static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    static String getPreorder(Node node) {
        StringBuilder sb = new StringBuilder();
        preorderHelper(node, sb);
        return sb.toString().trim();
    }

    static void preorderHelper(Node node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(" ");
        preorderHelper(node.left, sb);
        preorderHelper(node.right, sb);
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
        // ---- LL case: chain 30 -> 20 -> 10 ----
        Node llRoot = new Node(30);
        llRoot.left = new Node(20);
        llRoot.left.left = new Node(10);

        System.out.println("=== Case 1: LL Imbalance (Right Rotation) ===");
        System.out.println("LL pre-order before:          " + getPreorder(llRoot));
        System.out.println("LL in-order  before:          " + getInorder(llRoot));

        llRoot = rightRotate(llRoot);

        System.out.println("LL pre-order after rotation:  " + getPreorder(llRoot));
        System.out.println("LL in-order  after rotation:  " + getInorder(llRoot));

        // ---- RR case: chain 10 -> 20 -> 30 ----
        Node rrRoot = new Node(10);
        rrRoot.right = new Node(20);
        rrRoot.right.right = new Node(30);

        System.out.println("\n=== Case 2: RR Imbalance (Left Rotation) ===");
        System.out.println("RR pre-order before:          " + getPreorder(rrRoot));
        System.out.println("RR in-order  before:          " + getInorder(rrRoot));

        rrRoot = leftRotate(rrRoot);

        System.out.println("RR pre-order after rotation:  " + getPreorder(rrRoot));
        System.out.println("RR in-order  after rotation:  " + getInorder(rrRoot));

        System.out.println("\n=== Explanation ===");
        System.out.println("A rotation must never change the in-order traversal of a BST because the");
        System.out.println("in-order traversal of any valid BST always produces the keys in strictly");
        System.out.println("sorted order, and rotations are specifically designed to preserve the BST");
        System.out.println("ordering invariant across all nodes and subtrees.");
    }
}

