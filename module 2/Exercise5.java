public class Exercise5 {
 
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
        if (head == null || k <= 1) return head;

        Node dummy = new Node(0);
        dummy.next = head;
        
        Node prevGroupEnd = dummy;
        Node curr = head;

        while (curr != null) {
            Node groupStart = curr;
            int count = 0;
            
            // Reverse k nodes (or whatever is left if < k)
            Node prev = null;
            while (curr != null && count < k) {
                Node next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
                count++;
            }
            
            // Connect the newly reversed group to the previous group
            prevGroupEnd.next = prev;
            // The original start of this group is now its end; connect it to the remaining list
            groupStart.next = curr;
            
            // Move our previous group end pointer up
            prevGroupEnd = groupStart;
        }

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
        Node head = buildList(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        int k = 3;
 
        System.out.print("Before: "); print(head);
 
        head = reverseInGroups(head, k);
 
        System.out.print("After:  "); print(head);
    }
}
