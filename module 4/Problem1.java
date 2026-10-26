/**
 * Problem 1: Implement a Queue Using an Array (enqueue, dequeue).
 * 
 * Objective:
 * Implement fundamental FIFO queue operations (enqueue, dequeue, peekFront,
 * isEmpty, isFull) using a fixed-capacity primitive array.
 * 
 * Data Structure Invariants:
 * - front: Index where the next element will be dequeued from.
 * - rear: Index where the most recent element was enqueued.
 * - count: Current number of active elements in the queue (0 <= count <= CAPACITY).
 * 
 * Time Complexities:
 * - enqueue: O(1)
 * - dequeue: O(1)
 * - peekFront: O(1)
 * - isEmpty / isFull: O(1)
 * Space Complexity: O(CAPACITY)
 */
public class Problem1 {

    // Maximum number of elements the queue can hold
    static final int CAPACITY = 100;

    // Underlying primitive array storage
    static int[] arr = new int[CAPACITY];

    // Index pointing to the front (oldest) element in the queue
    static int front = 0;

    // Index pointing to the rear (most recently added) element in the queue
    static int rear = -1;

    // Number of elements currently present in the queue
    static int count = 0;

    // Returns true if the queue contains no elements
    static boolean isEmpty() {
        return count == 0;
    }

    // Returns true if the queue has reached its maximum allocated capacity
    static boolean isFull() {
        return count == CAPACITY;
    }

    // Inserts a new integer value at the rear of the queue
    static void enqueue(int value) {
        // Guard check: prevent writing beyond the array boundaries
        if (isFull()) {
            System.out.println("Queue Overflow");
            return;
        }

        // Advance rear index to the next available position
        rear = rear + 1;

        // Store the value at the updated rear position
        arr[rear] = value;

        // Increment the total element count
        count++;
    }

    // Removes and returns the front (oldest) element from the queue
    static int dequeue() {
        // Guard check: cannot remove from an empty queue
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return -1; // Sentinel value indicating failure
        }

        // Retrieve the element at the current front index
        int value = arr[front];

        // Advance front index toward the rear
        front++;

        // Decrement the total element count
        count--;

        // Return the dequeued value
        return value;
    }

    // Retrieves the front element without removing it from the queue
    static int peekFront() {
        // Guard check: ensure queue is not empty
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1; // Sentinel value indicating failure
        }

        // Return the element stored at the front index
        return arr[front];
    }

    // Main method to test and demonstrate basic queue operations
    public static void main(String[] args) {
        System.out.println("=== Testing ArrayQueue (Problem 1) ===");

        // Enqueue three initial elements
        enqueue(10);
        enqueue(20);
        enqueue(30);

        // Inspect the front element: expected 10
        System.out.println("Front: " + peekFront());

        // Dequeue the front element: expected 10
        System.out.println("Dequeue: " + dequeue());

        // Enqueue another element
        enqueue(40);

        // Inspect front element after dequeue: expected 20
        System.out.println("Front: " + peekFront());

        // Check if queue is empty: expected false
        System.out.println("Is empty? " + isEmpty());
    }
}
