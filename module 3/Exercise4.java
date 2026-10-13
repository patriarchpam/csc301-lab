// Import Stack class from java.util
import java.util.Stack;

/**
 * Exercise 4: Memory-Optimized MinStack
 * 
 * Trade-off Comparison:
 * ---------------------------------------------------------------------------------
 * Metric             | Original MinStack               | Optimized MinStack
 * ---------------------------------------------------------------------------------
 * minStack Size      | Exactly N elements always.      | Only stores when min changes
 *                    | (1:1 with mainStack).           | (between 1 and N elements).
 * ---------------------------------------------------------------------------------
 * Best-case Space    | O(N) auxiliary space            | O(1) auxiliary space
 *                    |                                 | (e.g., strictly increasing input).
 * ---------------------------------------------------------------------------------
 * Average-case Space | O(N) auxiliary space            | O(log N) auxiliary space
 *                    |                                 | for random input distributions.
 * ---------------------------------------------------------------------------------
 * Worst-case Space   | O(N) auxiliary space            | O(N) auxiliary space
 *                    |                                 | (strictly decreasing input or duplicates).
 * ---------------------------------------------------------------------------------
 * Time Complexity    | Push: O(1), Pop: O(1), Min: O(1)| Push: O(1), Pop: O(1), Min: O(1)
 *                    | No conditionals on pop.         | Single comparison on pop.
 * ---------------------------------------------------------------------------------
 */
public class Exercise4 {

    // Main stack holding all values
    static Stack<Integer> mainStack = new Stack<>();

    // Space-optimized min stack: only stores a new value when the minimum actually changes!
    static Stack<Integer> minStack = new Stack<>();

    // Pushes value onto mainStack and conditionally onto minStack
    static void push(int value) {
        // Always store the value in the primary stack
        mainStack.push(value);

        // Only push to minStack if it's empty OR the incoming value is <= current minimum
        // Note: "<=" is crucial to handle duplicates of the minimum correctly
        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value); // Record the new minimum
        }
        // If value > minStack.peek(), do NOT push anything to minStack (saves memory!)
    }

    // Removes and returns top element from stack
    static int pop() {
        // Underflow check: ensure mainStack has elements
        if (mainStack.isEmpty()) {
            System.out.println("Stack Underflow");
            return -1;
        }

        // Pop top element from the main storage
        int poppedValue = mainStack.pop();

        // If the popped element matches the current minimum, remove it from minStack as well
        if (poppedValue == minStack.peek()) {
            minStack.pop(); // Restore previous minimum
        }

        // Return the popped value to the caller
        return poppedValue;
    }

    // Returns current minimum in O(1) time
    static int getMin() {
        // Underflow check: verify stack is not empty
        if (minStack.isEmpty()) {
            System.out.println("Stack is empty");
            return -1;
        }
        // Peek top of minStack (O(1) time complexity)
        return minStack.peek();
    }

    // Main method to test optimization and duplicate handling
    public static void main(String[] args) {
        System.out.println("=== Testing Exercise 4: Optimized MinStack ===");

        // Push 5: mainStack=[5], minStack=[5] (size: 1 vs original 1)
        push(5);
        // Push 3: mainStack=[5, 3], minStack=[5, 3] (size: 2 vs original 2)
        push(3);
        // Push 7: mainStack=[5, 3, 7], minStack=[5, 3] (7 is ignored on minStack!) (size: 2 vs original 3)
        push(7);
        // Push 2: mainStack=[5, 3, 7, 2], minStack=[5, 3, 2] (size: 3 vs original 4)
        push(2);

        // Print stack sizes to prove memory savings
        System.out.println("Main stack size: " + mainStack.size());
        System.out.println("Min stack size:  " + minStack.size() + " (original would be " + mainStack.size() + ")");
        System.out.println("Current minimum: " + getMin()); // Returns 2

        // Popping 2: matches top of minStack, so minStack pops 2 -> new min becomes 3
        System.out.println("\nPopping: " + pop());
        System.out.println("Current minimum: " + getMin()); // Returns 3
        System.out.println("Min stack size: " + minStack.size());

        // Popping 7: does NOT match top of minStack (7 != 3), so minStack is unchanged!
        System.out.println("\nPopping: " + pop());
        System.out.println("Current minimum: " + getMin()); // Still 3
        System.out.println("Min stack size: " + minStack.size());

        // Demonstrate why "<=" was necessary instead of strictly "<"
        System.out.println("\n=== Testing Duplicate Minimums Handling ===");
        // Push 2: smaller than 3 -> pushed to minStack
        push(2);
        // Push duplicate 2: equal to 2 (<=) -> also pushed to minStack so both are recorded
        push(2);
        System.out.println("Pushed duplicate 2s. Current minimum: " + getMin());

        // Pop first 2: pops one copy from minStack
        System.out.println("Popping: " + pop());
        System.out.println("Current minimum after popping first 2: " + getMin()); // Still 2

        // Pop second 2: pops second copy from minStack
        System.out.println("Popping: " + pop());
        System.out.println("Current minimum after popping second 2: " + getMin()); // Returns to 3
    }
}
