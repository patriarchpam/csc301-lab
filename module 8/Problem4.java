import java.util.PriorityQueue;

/**
 * Problem 4: Merge k Sorted Linked Lists Using a Min-Heap.
 * 
 * Objective:
 * Combine k separate sorted linked lists into one fully sorted linked list
 * in O(n log k) time using a min-heap of size at most k.
 * 
 * Complexity:
 * - Time: O(n log k) where n is the total number of nodes across all k lists.
 * - Space: O(k) for the min-heap.
 */
public class Problem4 {

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
        // min-heap ordered by each node's data value
        PriorityQueue<Node> minHeap =
            new PriorityQueue<>((a, b) -> a.data - b.data);

        for (Node headNode : lists) {
            if (headNode != null) minHeap.offer(headNode);
        }

        Node dummy = new Node(0);
        Node tail = dummy;

        while (!minHeap.isEmpty()) {
            Node smallest = minHeap.poll();
            tail.next = smallest;
            tail = tail.next;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);   // push that list's next value
            }
        }

        return dummy.next;
    }

    static void printList(Node head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.data);
            if (head.next != null) sb.append(" -> ");
            head = head.next;
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Node[] lists = {
            buildList(new int[]{1, 4, 7}),
            buildList(new int[]{2, 5, 8}),
            buildList(new int[]{3, 6, 9}),
        };

        Node merged = mergeKLists(lists);

        System.out.print("Merged: ");
        printList(merged);
    }
}
