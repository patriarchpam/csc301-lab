// Import Stack class from java.util
import java.util.Stack;
 
/**
 * Problem 4: MinStack with O(1) Minimum Retrieval.
 * Keeps two parallel stacks: mainStack for standard storage, and minStack
 * tracking the minimum at every position (1:1 correspondence, O(N) space).
 */
public class Problem4 {
 
    // Stack that stores all elements pushed by the user in normal LIFO order
    static Stack<Integer> mainStack = new Stack<>();

    // Stack whose top element always reflects the minimum value currently in mainStack
    static Stack<Integer> minStack = new Stack<>();
 
    // Pushes a value onto the stack and updates the parallel minimum stack
    static void push(int value) {
        // Add the value to the main storage stack
        mainStack.push(value);

        // If minStack is empty or the new value is <= the current minimum:
        if (minStack.isEmpty() || value <= minStack.peek()) {
            // A new minimum has been found (or tie), so push this value
            minStack.push(value);
        } else {
            // The new value is larger than the current minimum,
            // so repeat the current minimum on minStack to maintain equal stack sizes
            minStack.push(minStack.peek());
        }
    }
 
    // Removes the top element from the stack and maintains synchronization
    static int pop() {
        // Pop from minStack to discard the minimum record corresponding to this entry
        minStack.pop();

        // Pop and return the actual value from mainStack
        return mainStack.pop();
    }
 
    // Returns the current minimum element in O(1) time without removing it
    static int getMin() {
        // The top of minStack is always the current minimum across all elements in mainStack
        return minStack.peek();
    }
 
    // Main method demonstrating push, pop, and getMin operations
    public static void main(String[] args) {
        // Push 5: mainStack=[5], minStack=[5] -> min is 5
        push(5);
        // Push 3: mainStack=[5, 3], minStack=[5, 3] -> min is 3
        push(3);
        // Push 7: mainStack=[5, 3, 7], minStack=[5, 3, 3] (3 repeated) -> min is 3
        push(7);
        // Push 2: mainStack=[5, 3, 7, 2], minStack=[5, 3, 3, 2] -> min is 2
        push(2);
 
        // Prints 2 (the current minimum)
        System.out.println("Current minimum: " + getMin());
 
        // Removes 2: both stacks pop their top element; top of minStack becomes 3
        pop();
        // Prints 3
        System.out.println("Current minimum: " + getMin());
 
        // Removes 7: both stacks pop their top element; top of minStack remains 3
        pop();
        // Prints 3
        System.out.println("Current minimum: " + getMin());
    }
}
