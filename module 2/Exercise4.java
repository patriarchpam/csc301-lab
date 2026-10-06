import java.util.PriorityQueue;

public class Exercise4 {
 
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
 
    static Node mergeKLists(Node[] lists) {
        if (lists == null || lists.length == 0) return null;
        
        // Priority Queue (Min-Heap) to keep track of the smallest current element among the k lists
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.data - b.data);
        
        // Add the head of each list to the priority queue
        for (Node node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }
        
        Node dummy = new Node(0);
        Node tail = dummy;
        
        // Continuously pop the smallest element and push its next element
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            tail.next = curr;
            tail = curr;
            
            if (curr.next != null) {
                pq.add(curr.next);
            }
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
        Node listA = buildList(new int[]{1, 4, 5});
        Node listB = buildList(new int[]{1, 3, 4});
        Node listC = buildList(new int[]{2, 6});
 
        System.out.print("List A: "); print(listA);
        System.out.print("List B: "); print(listB);
        System.out.print("List C: "); print(listC);
 
        Node[] lists = new Node[]{listA, listB, listC};
        Node merged = mergeKLists(lists);
 
        System.out.print("Merged: "); print(merged);
    }
}
