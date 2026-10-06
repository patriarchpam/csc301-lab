public class Problem1 {
 
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
 
    static void traverse() {
        Node temp = head;
        System.out.print("List: ");
        while (temp != null) {
            System.out.print(temp.data);
            if (temp.next != null) System.out.print(" -> ");
            temp = temp.next;
        }
        System.out.println(" -> NULL");
    }
 
    public static void main(String[] args) {
        insertAtEnd(10);
        insertAtEnd(25);
        insertAtEnd(40);
        insertAtEnd(55);
 
        traverse();
    }
}
