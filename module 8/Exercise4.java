import java.util.PriorityQueue;

/**
 * Exercise 4: Merge k Sorted Linked Lists With Source-List Provenance Tracking.
 * 
 * Objective:
 * Modify Problem 4 to also report which ORIGINAL list index each merged value
 * came from (e.g., '1 (from list 0), 2 (from list 1), 3 (from list 2),
 * 4 (from list 0), ...'). Use this to confirm, without just trusting the final
 * sorted output, that the heap is indeed always selecting the globally smallest
 * available candidate at each step.
 */
public class Exercise4 {

    static class Node {
        int data;
        int listIndex; // Tracks which input list (0 .. k-1) this node originated from
        Node next;

        Node(int data, int listIndex) {
            this.data = data;
            this.listIndex = listIndex;
        }
    }

    static Node buildList(int[] values, int listIndex) {
        Node head = null, tail = null;
        for (int v : values) {
            Node newNode = new Node(v, listIndex);
            if (head == null) { head = newNode; tail = newNode; }
            else { tail.next = newNode; tail = newNode; }
        }
        return head;
    }

    static Node mergeKListsWithTrace(Node[] lists) {
        PriorityQueue<Node> minHeap =
            new PriorityQueue<>((a, b) -> Integer.compare(a.data, b.data));

        for (Node headNode : lists) {
            if (headNode != null) minHeap.offer(headNode);
        }

        Node dummy = new Node(0, -1);
        Node tail = dummy;
        int step = 1;

        System.out.println("=== Step-by-Step Min-Heap Selection Trace ===");
        while (!minHeap.isEmpty()) {
            // Format current candidates in the heap before polling
            StringBuilder candidates = new StringBuilder("[");
            boolean first = true;
            for (Node n : minHeap) {
                if (!first) candidates.append(", ");
                candidates.append(n.data).append("(L").append(n.listIndex).append(")");
                first = false;
            }
            candidates.append("]");

            Node smallest = minHeap.poll();
            System.out.printf("  Step %d: Heap candidates = %-28s -> Selected: %d (from list %d)%n",
                    step++, candidates.toString(), smallest.data, smallest.listIndex);

            tail.next = smallest;
            tail = tail.next;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    static void printListWithProvenance(Node head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.data).append(" (from list ").append(head.listIndex).append(")");
            if (head.next != null) sb.append(", ");
            head = head.next;
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Node[] lists = {
            buildList(new int[]{1, 4, 7}, 0),
            buildList(new int[]{2, 5, 8}, 1),
            buildList(new int[]{3, 6, 9}, 2),
        };

        Node merged = mergeKListsWithTrace(lists);

        System.out.print("\nMerged with source list provenance:\n  ");
        printListWithProvenance(merged);
    }
}
