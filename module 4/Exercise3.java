/**
 * Exercise 3: Dynamic Resizing for a Circular Queue.
 * 
 * Objective:
 * Implement a resize() operation that doubles the array's capacity when the
 * circular queue becomes full, copying existing elements into the new enlarged
 * array in correct logical FIFO order (starting from front).
 * 
 * Critical Resizing Insight:
 * In a circular queue, elements may have wrapped around the end of the array,
 * meaning physical array order does NOT match logical FIFO order.
 * For example:
 * Physical array of capacity 4: [element3, element4, element1, element2]
 * Here, front is at index 2 and rear is at index 1.
 * If we simply copied elements by identical index into an array of size 8,
 * the wrapped elements would be disconnected and FIFO order corrupted!
 * 
 * Correct Unrolling Procedure:
 * We unroll the elements sequentially:
 * for (int i = 0; i < count; i++) {
 *     newArr[i] = arr[(front + i) % capacity];
 * }
 * Then reset: front = 0, rear = count - 1, and capacity = newCapacity.
 * 
 * Amortised Time Complexity:
 * Although resizing an array of size N takes O(N) copying work, resizing only
 * happens after N/2 new elements have been enqueued since the last resize.
 * Spreading the O(N) cost across those N/2 operations yields an AMORTISED
 * time complexity of O(1) per enqueue.
 */
public class Exercise3 {

    // Dynamic storage array
    private int[] arr;
    private int front;
    private int rear;
    private int count;
    private int capacity;

    // Constructor with initial capacity
    public Exercise3(int initialCapacity) {
        this.capacity = initialCapacity;
        this.arr = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == capacity;
    }

    public int size() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }

    // Doubles the capacity of the queue and unrolls elements into logical FIFO order
    private void resize() {
        int newCapacity = capacity * 2;
        int[] newArr = new int[newCapacity];

        System.out.println("\n>>> [RESIZING TRIGGERED] Capacity reached (" + capacity + "). Doubling to " + newCapacity + "...");

        // Display pre-resize physical layout
        System.out.print("    Pre-resize physical array: [ ");
        for (int i = 0; i < capacity; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println("] (front=" + front + ", rear=" + rear + ")");

        // Unroll circular elements starting from 'front' into indices 0 .. count-1
        for (int i = 0; i < count; i++) {
            newArr[i] = arr[(front + i) % capacity];
        }

        // Reset pointers to clean contiguous layout
        this.front = 0;
        this.rear = count - 1;
        this.capacity = newCapacity;
        this.arr = newArr;

        // Display post-resize physical layout
        System.out.print("    Post-resize physical array: [ ");
        for (int i = 0; i < capacity; i++) {
            if (i < count) System.out.print(arr[i] + " ");
            else System.out.print("- ");
        }
        System.out.println("] (front=" + front + ", rear=" + rear + ")\n");
    }

    // Enqueue with automatic dynamic expansion
    public void enqueue(int value) {
        // If queue is full, dynamically double capacity rather than overflowing
        if (isFull()) {
            resize();
        }

        // Advance rear cyclically
        rear = (rear + 1) % capacity;
        arr[rear] = value;
        count++;
        System.out.println("Enqueued: " + value + " -> (count=" + count + "/" + capacity 
                           + ", front=" + front + ", rear=" + rear + ")");
    }

    // Dequeues and returns the oldest element
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            return -1;
        }

        int value = arr[front];
        front = (front + 1) % capacity;
        count--;
        System.out.println("Dequeued: " + value + " -> (count=" + count + "/" + capacity 
                           + ", front=" + front + ", rear=" + rear + ")");
        return value;
    }

    // Retrieves front element without removal
    public int peekFront() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        return arr[front];
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 3: Dynamic Resizing Circular Queue With Logical Unrolling       ");
        System.out.println("==========================================================================");

        // Start with a small capacity of 4 to trigger resizing quickly
        Exercise3 q = new Exercise3(4);

        System.out.println("\n--- Step 1: Enqueue 3 elements into capacity 4 ---");
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);

        System.out.println("\n--- Step 2: Dequeue 2 elements to advance front pointer ---");
        q.dequeue(); // removes 10 (front moves to 1)
        q.dequeue(); // removes 20 (front moves to 2)

        System.out.println("\n--- Step 3: Enqueue elements to force wrap-around ---");
        q.enqueue(40); // index 3
        q.enqueue(50); // index 0 (wraps around!)
        q.enqueue(60); // index 1 (queue is now completely full: 4/4)

        System.out.println("\n--- Step 4: Enqueue another element (70) to trigger resize() ---");
        // This MUST trigger resize and properly unroll wrapped elements
        q.enqueue(70);

        System.out.println("\n--- Step 5: Enqueue more elements into enlarged capacity ---");
        q.enqueue(80);
        q.enqueue(90);

        System.out.println("\n--- Step 6: Dequeue all elements to verify strict FIFO order ---");
        System.out.print("Drain order: ");
        while (!q.isEmpty()) {
            System.out.print(q.dequeue() + " ");
        }
        System.out.println();

        System.out.println("\n=== Complexity Analysis Summary ===");
        System.out.println("- Worst-case time for enqueue when resizing: O(N) due to array reallocation and copying.");
        System.out.println("- Amortised time for enqueue across all operations: O(1).");
        System.out.println("- Dequeue time complexity: strictly O(1) worst-case.");
        System.out.println("- Space complexity: O(N) where N is current capacity.");
    }
}
