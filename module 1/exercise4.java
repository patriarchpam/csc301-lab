class MergeSortedLists {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    static Node buildList(int[] values) {
        Node head = null, tail = null;
        for (int v : values) {
            Node newNode = new Node(v);
            if (head == null) { head = newNode; tail = newNode; }
            else { tail.next = newNode; tail = newNode; }
        }
        return head;
    }

    static Node merge(Node a, Node b) {
        Node dummy = new Node(0);   // dummy node simplifies edge cases
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) { tail.next = a; a = a.next; }
            else { tail.next = b; b = b.next; }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;  // attach whichever list remains

        return dummy.next;
    }

    static void print(Node head) {
        while (head != null) {
            System.out.print(head.data);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println(" -> NULL");
    }

    public static void main(String[] args) {
        Node listA = buildList(new int[]{1, 3, 5});
        Node listB = buildList(new int[]{2, 4, 6});

        System.out.print("List A: "); print(listA);
        System.out.print("List B: "); print(listB);

        Node merged = merge(listA, listB);

        System.out.print("Merged: "); print(merged);
    }
}
