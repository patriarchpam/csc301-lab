/**
 * Problem 1: Fixed-Capacity Stack Implementation Using a Raw Array.
 */
public class Problem1 {
 
    // Maximum number of elements the stack can hold at any given time
    static final int CAPACITY = 100;

    // Primitive integer array allocated with a fixed size of 100
    static int[] arr = new int[CAPACITY];

    // Tracks index of the topmost element; initialized to -1 (indicating stack is empty)
    static int top = -1;

    // Returns true if stack contains zero elements (top is -1)
    static boolean isEmpty() { 
        return top == -1; 
    }

    // Returns true if stack has reached its maximum capacity (top is CAPACITY - 1, i.e., 99)
    static boolean isFull() { 
        return top == CAPACITY - 1; 
    }
 
    // Inserts a new value onto the stack
    static void push(int value) {
        // If stack is full, prevent writing out of bounds and report Stack Overflow
        if (isFull()) { 
            System.out.println("Stack Overflow"); 
            return; 
        }
        // Pre-increment top first (++top moves index from -1 to 0 on first push),
        // then store the value into arr at that index
        arr[++top] = value;
    }
 
    // Removes and returns the topmost element
    static int pop() {
        // Guard check: cannot pop from an empty stack (Stack Underflow)
        if (isEmpty()) { 
            System.out.println("Stack Underflow"); 
            return -1; // Sentinel value indicating failure
        }
        // Post-decrement: returns arr[top] first, then decrements top by 1
        return arr[top--];
    }
 
    // Returns the topmost element without removing it
    static int peek() {
        // Guard check: cannot peek into an empty stack
        if (isEmpty()) { 
            System.out.println("Stack is empty"); 
            return -1; // Sentinel value indicating failure
        }
        // Returns the element at the current top index
        return arr[top];
    }
 
    // Main method to test basic operations
    public static void main(String[] args) {
        // Push 10: top becomes 0, arr[0] = 10
        push(10);
        // Push 20: top becomes 1, arr[1] = 20
        push(20);
        // Push 30: top becomes 2, arr[2] = 30
        push(30);

        // Peek returns arr[2] which is 30 without modifying top
        System.out.println("Peek: " + peek());
 
        // Pop returns arr[2] (30) and decrements top to 1
        System.out.println("Pop: " + pop());
        // Pop returns arr[1] (20) and decrements top to 0
        System.out.println("Pop: " + pop());
 
        // Push 40: top increments to 1, arr[1] = 40 (stack now contains [10, 40])
        push(40);

        // Peek returns arr[1] which is 40
        System.out.println("Peek: " + peek());

        // Checks whether top == -1 (prints false because top is 1)
        System.out.println("Is empty? " + isEmpty());
    }
}
