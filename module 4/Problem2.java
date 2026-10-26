// Import Java's built-in Stack collection
import java.util.Stack;

/**
 * Problem 2: Implement a FIFO Queue Using Two LIFO Stacks.
 * 
 * Objective:
 * Construct a First-In-First-Out (FIFO) queue using two Last-In-First-Out
 * (LIFO) stacks as underlying storage without any array/linked-list queue logic.
 * 
 * Theory / Mechanism:
 * - A single stack reverses element order (LIFO).
 * - Reversing a sequence TWICE restores the original order (FIFO).
 * - inStack: Receives all newly enqueued elements.
 * - outStack: Serves all dequeue requests.
 * - When outStack is empty, all elements are popped from inStack and pushed
 *   onto outStack. This places the oldest enqueued element on top of outStack.
 * - Subsequent dequeues pop directly from outStack in O(1) until it empties.
 * 
 * Complexity:
 * - Enqueue: O(1) worst-case
 * - Dequeue: O(1) amortised (occasional O(N) when transferring from inStack)
 * - Space: O(N) total across both stacks
 */
public class Problem2 {

    // Stack dedicated to receiving newly enqueued items
    static Stack<Integer> inStack = new Stack<>();

    // Stack dedicated to serving dequeue calls
    static Stack<Integer> outStack = new Stack<>();

    // Pushes a new element onto the input stack
    static void enqueue(int value) {
        // Enqueue is always a simple O(1) operation onto inStack
        inStack.push(value);
    }

    // Removes and returns the oldest element from the queue
    static int dequeue() {
        // If outStack has no elements left, transfer everything from inStack
        if (outStack.isEmpty()) {
            // Pour all elements from inStack into outStack, reversing their order
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }

        // If outStack is still empty after the transfer attempt, the entire queue is empty
        if (outStack.isEmpty()) {
            System.out.println("Queue Underflow");
            return -1; // Sentinel value indicating empty queue
        }

        // The top of outStack is the oldest remaining element in the queue
        return outStack.pop();
    }

    // Main method to test and demonstrate FIFO queue behavior using two stacks
    public static void main(String[] args) {
        System.out.println("=== Testing Queue Using Two Stacks (Problem 2) ===");

        // Enqueue 1, 2, 3
        enqueue(1);
        enqueue(2);
        enqueue(3);

        // Dequeue first element: expect 1 (FIFO order)
        System.out.println("Dequeue: " + dequeue());

        // Enqueue 4 while outStack still holds elements (2 and 3)
        enqueue(4);

        // Dequeue remaining elements in FIFO order
        System.out.println("Dequeue: " + dequeue()); // expect 2
        System.out.println("Dequeue: " + dequeue()); // expect 3
        System.out.println("Dequeue: " + dequeue()); // expect 4
    }
}
