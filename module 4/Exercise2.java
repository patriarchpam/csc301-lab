// Import Java's built-in Stack collection
import java.util.Stack;

/**
 * Exercise 2: Execution Trace and Amortised Cost Analysis for Two-Stack Queue.
 * 
 * Objective:
 * Trace through the four dequeue() calls in the sample execution sequence:
 * 1. enqueue(1), enqueue(2), enqueue(3)
 * 2. dequeue() -> returns 1
 * 3. enqueue(4)
 * 4. dequeue() -> returns 2
 * 5. dequeue() -> returns 3
 * 6. dequeue() -> returns 4
 * 
 * Identify EXACTLY which dequeue call triggers the O(n) transfer from inStack
 * to outStack, which calls run in O(1) time because outStack is already non-empty,
 * and display the results in a formatted execution trace table.
 */
public class Exercise2 {

    static Stack<Integer> inStack = new Stack<>();
    static Stack<Integer> outStack = new Stack<>();

    // Record structure for our trace table
    static class TraceStep {
        int callNumber;
        String operation;
        String inStackBefore;
        String outStackBefore;
        boolean transferTriggered;
        int itemsTransferred;
        String inStackAfter;
        String outStackAfter;
        int returnedValue;
        String callCost;

        TraceStep(int callNumber, String operation, String inBefore, String outBefore,
                  boolean transfer, int transferredCount, String inAfter, String outAfter,
                  int retVal, String cost) {
            this.callNumber = callNumber;
            this.operation = operation;
            this.inStackBefore = inBefore;
            this.outStackBefore = outBefore;
            this.transferTriggered = transfer;
            this.itemsTransferred = transferredCount;
            this.inStackAfter = inAfter;
            this.outStackAfter = outAfter;
            this.returnedValue = retVal;
            this.callCost = cost;
        }
    }

    // Instrumented enqueue method
    static void enqueue(int value) {
        inStack.push(value);
        System.out.println("-> Action: enqueue(" + value + ") | inStack: " + inStack + " | outStack: " + outStack);
    }

    // Instrumented dequeue method that tracks transfers and step metrics
    static TraceStep instrumentedDequeue(int callNum) {
        String inBefore = inStack.toString();
        String outBefore = outStack.toString();
        boolean transfer = false;
        int transferred = 0;

        // Check if transfer is required
        if (outStack.isEmpty()) {
            transfer = true;
            transferred = inStack.size();
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }

        int val = outStack.isEmpty() ? -1 : outStack.pop();
        String inAfter = inStack.toString();
        String outAfter = outStack.toString();
        String cost = transfer ? ("O(N) [N=" + transferred + "]") : "O(1) [Direct pop]";

        return new TraceStep(callNum, "dequeue()", inBefore, outBefore, transfer, 
                             transferred, inAfter, outAfter, val, cost);
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 2: Two-Stack Queue Step-by-Step Trace & Cost Analysis           ");
        System.out.println("==========================================================================");

        // Pre-fill with first three elements
        System.out.println("\n--- Phase 1: Enqueue elements 1, 2, 3 ---");
        enqueue(1);
        enqueue(2);
        enqueue(3);

        TraceStep[] trace = new TraceStep[4];

        // Call 1: Dequeue
        System.out.println("\n--- Executing Dequeue Call #1 ---");
        trace[0] = instrumentedDequeue(1);

        // Enqueue 4
        System.out.println("\n--- Phase 2: Enqueue element 4 ---");
        enqueue(4);

        // Call 2: Dequeue
        System.out.println("\n--- Executing Dequeue Call #2 ---");
        trace[1] = instrumentedDequeue(2);

        // Call 3: Dequeue
        System.out.println("\n--- Executing Dequeue Call #3 ---");
        trace[2] = instrumentedDequeue(3);

        // Call 4: Dequeue
        System.out.println("\n--- Executing Dequeue Call #4 ---");
        trace[3] = instrumentedDequeue(4);

        // Display trace table
        System.out.println("\n====================================================================================================================");
        System.out.println("                                         DEQUEUE CALLS TRACE TABLE                                                  ");
        System.out.println("====================================================================================================================");
        System.out.printf("%-6s | %-15s | %-15s | %-16s | %-12s | %-15s | %-15s | %-6s | %-18s%n",
                "Call #", "inStack Before", "outStack Before", "Transfer Fired?", "Transferred", 
                "inStack After", "outStack After", "Return", "Time Complexity");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        for (TraceStep step : trace) {
            System.out.printf("%-6d | %-15s | %-15s | %-16b | %-12d | %-15s | %-15s | %-6d | %-18s%n",
                    step.callNumber, step.inStackBefore, step.outStackBefore, 
                    step.transferTriggered, step.itemsTransferred, 
                    step.inStackAfter, step.outStackAfter, step.returnedValue, step.callCost);
        }
        System.out.println("====================================================================================================================");

        // In-depth written explanation
        System.out.println("\n=== Detailed Analysis ===");
        System.out.println("1. Call #1 (dequeue -> 1):");
        System.out.println("   - outStack was EMPTY [].");
        System.out.println("   - Triggers the O(N) transfer of 3 items (1, 2, 3) from inStack to outStack.");
        System.out.println("   - inStack becomes [], outStack becomes [3, 2, 1]. Item 1 is popped from the top.");
        System.out.println("   - Time Complexity: O(3) = O(N).");
        System.out.println();
        System.out.println("2. Call #2 (dequeue -> 2):");
        System.out.println("   - outStack was NON-EMPTY [3, 2].");
        System.out.println("   - No transfer is triggered. The top element (2) is popped directly.");
        System.out.println("   - Time Complexity: O(1) worst-case.");
        System.out.println();
        System.out.println("3. Call #3 (dequeue -> 3):");
        System.out.println("   - outStack was NON-EMPTY [3].");
        System.out.println("   - No transfer is triggered. The top element (3) is popped directly.");
        System.out.println("   - Time Complexity: O(1) worst-case.");
        System.out.println();
        System.out.println("4. Call #4 (dequeue -> 4):");
        System.out.println("   - outStack was EMPTY [] because all previous elements (1, 2, 3) had been consumed.");
        System.out.println("   - Triggers another transfer from inStack (which holds [4]).");
        System.out.println("   - inStack becomes [], outStack becomes [4], and 4 is popped.");
        System.out.println("   - Time Complexity: O(1) (transferring 1 element).");
        System.out.println();
        System.out.println("Amortised Guarantee:");
        System.out.println("Each element is pushed onto inStack once (O(1)), moved to outStack once (O(1)),");
        System.out.println("and popped from outStack once (O(1)). Across N elements, total work is 3N = O(N),");
        System.out.println("yielding an average/amortised time of O(1) per operation!");
    }
}
