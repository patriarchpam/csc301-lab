/**
 * Problem 3: Design a Circular Queue With a Fixed Size.
 * 
 * Objective:
 * Implement a fixed-capacity queue that reuses vacated array slots by wrapping
 * the front and rear indices back to the beginning of the array, eliminating
 * the lost slots limitation identified in Problem 1.
 * 
 * Theory / Mechanism:
 * - A circular queue treats the backing array as a logical ring.
 * - Indices advance using modulo arithmetic: index = (index + 1) % capacity.
 * - By tracking an explicit 'count' variable, we remove any ambiguity
 *   between the full and empty states (where front == rear could mean either).
 * 
 * Complexity:
 * - enqueue: O(1)
 * - dequeue: O(1)
 * - peekFront: O(1)
 * - Space: O(capacity)
 */
public class Problem3 {

    // Primitive array to hold circular queue elements
    int[] arr;

    // front: index of current front element
    // rear: index of most recently enqueued element
    // count: number of active elements
    // capacity: total slots in the array
    int front, rear, count, capacity;

    // Constructor to initialize queue with a fixed capacity
    public Problem3(int capacity) {
        this.capacity = capacity;
        this.arr = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
    }

    // Returns true if the queue holds zero elements
    boolean isEmpty() {
        return count == 0;
    }

    // Returns true if all slots in the circular array are occupied
    boolean isFull() {
        return count == capacity;
    }

    // Inserts an element at the rear, wrapping around to index 0 if needed
    void enqueue(int value) {
        // Guard check: cannot enqueue into a full circular queue
        if (isFull()) {
            System.out.println("Queue Overflow");
            return;
        }

        // Advance rear cyclically using modulo arithmetic
        rear = (rear + 1) % capacity;

        // Store the value into the array slot
        arr[rear] = value;

        // Increment active element count
        count++;
    }

    // Removes and returns the front element, wrapping front pointer if needed
    int dequeue() {
        // Guard check: cannot dequeue from an empty queue
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return -1; // Sentinel value indicating failure
        }

        // Retrieve element at the current front index
        int value = arr[front];

        // Advance front cyclically using modulo arithmetic
        front = (front + 1) % capacity;

        // Decrement active element count
        count--;

        // Return the dequeued value
        return value;
    }

    // Retrieves the front element without removing it
    int peekFront() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return arr[front];
    }

    // Main method to test circular wrap-around behavior
    public static void main(String[] args) {
        System.out.println("=== Testing CircularQueue (Problem 3) ===");

        // Create circular queue of capacity 5
        Problem3 q = new Problem3(5);

        // Enqueue three elements
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        // Dequeue two elements, vacating slots 0 and 1
        System.out.println("Dequeue: " + q.dequeue()); // removes 10
        System.out.println("Dequeue: " + q.dequeue()); // removes 20

        // Enqueue enough elements to force wrap-around past index 4
        q.enqueue(40); // index 3
        q.enqueue(50); // index 4
        q.enqueue(60); // rear wraps back to index 0!
        q.enqueue(70); // index 1 (fills the queue to 5/5 capacity)

        // Verify state
        System.out.println("Front: " + q.peekFront()); // expect 30
        System.out.println("Is full? " + q.isFull());   // 5/5 slots used (true)
    }
}
