class ReverseList {

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
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;  // save next node
            curr.next = prev;       // reverse the pointer
            prev = curr;            // move prev forward
            curr = next;            // move curr forward
        }
        head = prev;                // prev is the new head
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
