public class Exercise2 {
 
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }
 
    static Node head;
 
    static void insertAtEnd(int value) {
        Node newNode = new Node(value);
        if (head == null) { head = newNode; return; }
        Node temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = newNode;
    }
 
    static void reverse() {
        head = reverseRecursive(head);
    }

    static Node reverseRecursive(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node newHead = reverseRecursive(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;
    }
 
    static void print() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data);
            if (temp.next != null) System.out.print(" -> ");
            temp = temp.next;
        }
        System.out.println(" -> NULL");
    }
 
    public static void main(String[] args) {
        insertAtEnd(10);
        insertAtEnd(20);
        insertAtEnd(30);
        insertAtEnd(40);
 
        System.out.print("Before: ");
        print();
 
        reverse();
 
        System.out.print("After:  ");
        print();
    }
}
