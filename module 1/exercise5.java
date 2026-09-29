class ReverseInGroupsOfK {

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

    static Node reverseInGroups(Node head, int k) {
        if (head == null) return null;

        Node curr = head;
        Node prev = null;
        int count = 0;

        // reverse the first k nodes (or fewer, if the list runs out)
        while (curr != null && count < k) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }

        // head is now the tail of this reversed group;
        // its "next" must point to the head of the next reversed group.
        if (curr != null) {
            head.next = reverseInGroups(curr, k);
        }

        return prev;   // prev is the new head of this group
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
        Node head = buildList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        int k = 3;

        System.out.print("Before: "); print(head);

        head = reverseInGroups(head, k);

        System.out.print("After:  "); print(head);
    }
}
