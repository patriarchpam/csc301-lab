// Import the ArrayList class from Java's utility package for a resizable array implementation
import java.util.ArrayList;

/**
 * Exercise 1: Dynamic Stack implementation using java.util.ArrayList.
 * 
 * Trade-offs Comparison:
 * -------------------------------------------------------------------------
 * Feature               | Raw Array Stack             | ArrayList Stack
 * -------------------------------------------------------------------------
 * Capacity / Overflow   | Fixed capacity. Throws      | Dynamically resizes. No
 *                       | "Stack Overflow" when full. | overflow until heap exhausted.
 * -------------------------------------------------------------------------
 * Time Complexity       | Strictly O(1) for push,     | Amortized O(1) push (O(N) on
 *                       | pop, and peek.              | resize); O(1) pop and peek.
 * -------------------------------------------------------------------------
 * Memory Overhead       | Allocates full capacity     | Allocates on demand, but has
 *                       | upfront; no object wrapper. | wrapper overhead (Integer) &
 *                       |                             | extra capacity buffer.
 * -------------------------------------------------------------------------
 * Autoboxing Overhead   | None (primitive int).       | Yes (int <-> Integer boxing).
 * -------------------------------------------------------------------------
 */
public class Exercise1 {

    // Internal dynamic storage: ArrayList of Integer objects to hold stack elements
    // Uses the Integer wrapper class because Java generics cannot store primitive types (int)
    static ArrayList<Integer> stack = new ArrayList<>();

    // Checks whether the stack contains any elements
    static boolean isEmpty() {
        // Returns true if size is 0, false otherwise
        return stack.isEmpty();
    }

    // Pushes a new integer value onto the top of the stack
    static void push(int value) {
        // Autoboxing converts the primitive 'int' into an 'Integer' object
        // ArrayList.add() appends to the end of the list in O(1) amortized time
        // The end of the ArrayList (index size - 1) serves as the "top" of our stack
        stack.add(value);
    }

    // Removes and returns the top element from the stack
    static int pop() {
        // Guard check: ensure stack is not empty before attempting to remove
        if (isEmpty()) {
            // Print error message indicating underflow condition
            System.out.println("Stack Underflow");
            // Return sentinel value -1 to indicate failure/empty stack
            return -1;
        }
        // stack.size() - 1 is the index of the top element
        // stack.remove() deletes the element at that index and returns it
        // Java automatically unboxes the returned Integer object into a primitive int
        return stack.remove(stack.size() - 1);
    }

    // Returns the top element without removing it from the stack
    static int peek() {
        // Guard check: ensure stack has elements to inspect
        if (isEmpty()) {
            // Print error message indicating stack is empty
            System.out.println("Stack is empty");
            // Return sentinel value -1 to indicate no element exists
            return -1;
        }
        // stack.get() retrieves the element at the last index (top of stack) in O(1) time
        return stack.get(stack.size() - 1);
    }

    // Returns the total number of elements currently stored in the stack
    static int size() {
        // stack.size() returns the count of items currently in the ArrayList
        return stack.size();
    }

    // Main method to run and demonstrate all stack operations
    public static void main(String[] args) {
        // Print header for the demonstration section
        System.out.println("=== Demonstrating Dynamic ArrayList Stack ===");

        // Push 10: Stack becomes [10], top is 10
        push(10);
        // Push 20: Stack becomes [10, 20], top is 20
        push(20);
        // Push 30: Stack becomes [10, 20, 30], top is 30
        push(30);

        // Peek at top element: returns 30 without removing it from stack
        System.out.println("Peek: " + peek());

        // Pop top element: removes and returns 30; stack becomes [10, 20]
        System.out.println("Pop: " + pop());

        // Pop next element: removes and returns 20; stack becomes [10]
        System.out.println("Pop: " + pop());

        // Push 40: Stack becomes [10, 40], top is 40
        push(40);

        // Peek at new top element: prints 40
        System.out.println("Peek: " + peek());

        // Display current element count: prints 2 (elements 10 and 40)
        System.out.println("Current Size: " + size());

        // Check if stack is empty: prints false because size is 2
        System.out.println("Is empty? " + isEmpty());

        // Print header demonstrating dynamic growth beyond raw array capacity
        System.out.println("\nPushing 150 elements to prove no Stack Overflow occurs:");

        // Loop 150 times, pushing integers 1 through 150 onto the stack
        // In Problem 1, the raw array had a fixed CAPACITY of 100 which would overflow
        for (int i = 1; i <= 150; i++) {
            // Push current iteration value 'i' onto the dynamic stack
            push(i);
        }

        // Print new total size: 2 previous elements + 150 newly added = 152 elements
        System.out.println("Successfully pushed 150 elements! Stack size: " + size());

        // Peek at the last pushed element: prints 150 (the value from the final iteration)
        System.out.println("Top element: " + peek());

        // Print section header for trade-offs comparison
        System.out.println("\n=== Trade-offs Summary ===");

        // Print trade-off point 1: raw array characteristics (fixed speed vs overflow risk)
        System.out.println("1. Raw Array: Strictly O(1) operations, zero boxing overhead, but fixed capacity limits cause Stack Overflow.");

        // Print trade-off point 2: ArrayList characteristics (dynamic safety vs resize/boxing overhead)
        System.out.println("2. ArrayList: Dynamic growth eliminates Stack Overflow, but incurs amortized resizing cost and Integer boxing overhead.");
    }
}
