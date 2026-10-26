/**
 * Exercise 1: Demonstrating the "Lost Slots" / False Overflow Limitation
 * of a Non-Circular Linear Array Queue.
 * 
 * Objective:
 * Enqueue and dequeue elements repeatedly until front advances past several
 * array slots. Then attempt to enqueue enough elements to fill the remaining
 * capacity. Confirm experimentally that "Queue Overflow" is triggered even
 * while the active element count is well below the array's total capacity.
 * 
 * Explanation of the Limitation:
 * In a naive linear array queue:
 * - 'rear' only increments on enqueue.
 * - 'front' only increments on dequeue.
 * Neither index ever wraps back around to index 0. Therefore, once elements are
 * dequeued, the slots preceding 'front' become permanently vacated and unreachable.
 * When 'rear' reaches CAPACITY - 1, the queue cannot accept new elements even if
 * front has advanced to index 3 or 4, leaving most array slots completely empty.
 * This motivates the Circular Queue data structure (Problem 3).
 */
public class Exercise1 {

    // Small fixed capacity to easily demonstrate slot exhaustion
    static final int CAPACITY = 5;

    // Fixed-size primitive storage array
    static int[] arr = new int[CAPACITY];

    // Pointer to the index of the oldest active element
    static int front = 0;

    // Pointer to the index of the most recently enqueued element
    static int rear = -1;

    // Number of currently active elements in the queue
    static int count = 0;

    // Checks if no elements are currently stored
    static boolean isEmpty() {
        return count == 0;
    }

    // Checks if the rear pointer has reached the end of the array
    static boolean isFull() {
        // In a linear array queue, overflow occurs when rear reaches the final array index,
        // OR when count == CAPACITY. Here we check rear to demonstrate slot starvation.
        return rear == CAPACITY - 1;
    }

    // Attempts to insert an element into the queue
    static boolean enqueue(int value) {
        if (isFull()) {
            System.out.println("-> [OVERFLOW] Cannot enqueue " + value 
                               + "! (rear = " + rear + ", capacity = " + CAPACITY 
                               + ", active count = " + count + ")");
            return false;
        }
        rear++;
        arr[rear] = value;
        count++;
        System.out.println("-> Enqueued: " + value + " at index [" + rear + "]");
        return true;
    }

    // Dequeues the oldest element and advances front
    static int dequeue() {
        if (isEmpty()) {
            System.out.println("-> [UNDERFLOW] Queue is empty!");
            return -1;
        }
        int val = arr[front];
        System.out.println("-> Dequeued: " + val + " from index [" + front + "]");
        front++;
        count--;
        return val;
    }

    // Visualizes the physical array slots and pointer locations
    static void displayQueueState() {
        System.out.println("\n--- Current Queue Internal State ---");
        System.out.println("Capacity: " + CAPACITY + " | Active Count: " + count 
                           + " | Front index: " + front + " | Rear index: " + rear);
        System.out.print("Array Slots: [");
        for (int i = 0; i < CAPACITY; i++) {
            if (i < front || i > rear) {
                System.out.print(" (vacated) ");
            } else {
                System.out.print(" " + arr[i] + " ");
            }
            if (i < CAPACITY - 1) System.out.print("|");
        }
        System.out.println("]");
        System.out.println("Available free slots in memory: " + (CAPACITY - count));
        System.out.println("-------------------------------------\n");
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println(" Exercise 1: Demonstrating Linear Array Queue Slot Exhaustion ");
        System.out.println("===============================================================");

        // Step 1: Enqueue 3 elements into queue of capacity 5
        System.out.println("\nStep 1: Enqueuing initial elements (10, 20, 30)...");
        enqueue(10);
        enqueue(20);
        enqueue(30);
        displayQueueState();

        // Step 2: Dequeue 2 elements, advancing front to index 2
        System.out.println("Step 2: Dequeuing 2 elements (10 and 20)...");
        dequeue();
        dequeue();
        displayQueueState();

        // Step 3: Now attempt to enqueue 3 more elements (40, 50, 60)
        // Notice: Active count is currently only 1, so the queue logically has 4 empty spots.
        System.out.println("Step 3: Enqueuing 40, 50, and 60...");
        enqueue(40); // fits at index 3
        enqueue(50); // fits at index 4 (last slot in array!)
        displayQueueState();

        // Attempting to enqueue 60 fails because rear is at index 4 (CAPACITY - 1)
        System.out.println("Step 4: Attempting to enqueue 60...");
        boolean success = enqueue(60);

        System.out.println("\n=== Experimental Confirmation & Analysis ===");
        System.out.println("Enqueue success for 60? " + success);
        System.out.println("Total array capacity: " + CAPACITY);
        System.out.println("Active elements currently in queue: " + count);
        System.out.println("Empty / wasted slots at front of array: " + front);
        System.out.println("\nConclusion:");
        System.out.println("Even though " + (CAPACITY - count) + " slots are completely empty,");
        System.out.println("the queue cannot store any more elements because front only moves forward.");
        System.out.println("This 'false overflow' problem is solved by Circular Queues (Problem 3),");
        System.out.println("which use modulo arithmetic: rear = (rear + 1) % CAPACITY to wrap around.");
    }
}
