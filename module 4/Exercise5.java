// Import Java's double-ended queue collections
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Arrays;

/**
 * Exercise 5: Sliding Window Minimum Using a Monotonic Deque.
 * 
 * Objective:
 * Adapt the sliding window maximum algorithm to compute the sliding window MINIMUM
 * by reversing the comparison direction in the deque removal condition:
 * Change: arr[deque.peekLast()] <= arr[i]
 * To:     arr[deque.peekLast()] >= arr[i]
 * 
 * Theory / Mechanism:
 * - For a sliding window MINIMUM, the deque maintains candidate indices in
 *   monotonically INCREASING order of array values.
 * - Any index at the back of the deque whose value is GREATER than or EQUAL to arr[i]
 *   can never be the window minimum as long as arr[i] is in the window.
 * - The FRONT of the deque always holds the index of the minimum element in the window.
 * 
 * Test Configuration:
 * Array: [1, 3, -1, -3, 5, 3, 6, 7], Window size k = 3
 * Expected Output: [-1, -3, -3, -3, 3, 3]
 */
public class Exercise5 {

    // Computes the minimum of each sliding window of size k
    static int[] minSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        if (n == 0 || k <= 0) return new int[0];

        int[] result = new int[n - k + 1];
        // Monotonic deque storing indices in INCREASING order of arr values
        Deque<Integer> deque = new ArrayDeque<>();

        System.out.println("Step-by-step Deque Trace for Sliding Window Minimum:");
        System.out.printf("%-6s | %-8s | %-20s | %-20s | %-10s%n", 
                          "Index", "Element", "Deque Before Insert", "Deque After Insert", "Window Min");
        System.out.println("-----------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            String dequeBefore = deque.toString();

            // KEY ADAPTATION: Remove indices whose values are GREATER than or EQUAL to arr[i]
            // In Problem 5 (maximum), the condition was: arr[deque.peekLast()] <= arr[i]
            while (!deque.isEmpty() && arr[deque.peekLast()] >= arr[i]) {
                deque.pollLast();
            }

            // Append current index to rear of deque
            deque.addLast(i);

            // Evict front index if it has fallen outside the current window of size k
            if (deque.peekFirst() == i - k) {
                deque.pollFirst();
            }

            String minStr = "-";
            // Record result once full window of size k is formed
            if (i >= k - 1) {
                int minVal = arr[deque.peekFirst()];
                result[i - k + 1] = minVal;
                minStr = String.valueOf(minVal);
            }

            System.out.printf("%-6d | %-8d | %-20s | %-20s | %-10s%n", 
                              i, arr[i], dequeBefore, deque.toString(), minStr);
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 5: Sliding Window Minimum Using a Monotonic Deque                ");
        System.out.println("==========================================================================");

        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;

        System.out.println("Input Array: " + Arrays.toString(arr));
        System.out.println("Window Size k = " + k + "\n");

        int[] minResult = minSlidingWindow(arr, k);

        System.out.println("\n=== Final Results ===");
        System.out.println("Computed Window Minimums: " + Arrays.toString(minResult));
        System.out.println("Expected Window Minimums: [-1, -3, -3, -3, 3, 3]");

        // Verification check
        int[] expected = {-1, -3, -3, -3, 3, 3};
        boolean matches = Arrays.equals(minResult, expected);
        System.out.println("Matches Expected Output? " + (matches ? "YES (PASSED)" : "NO (FAILED)"));

        System.out.println("\n=== Explanation of Comparison Change ===");
        System.out.println("1. Problem 5 (Maximum):");
        System.out.println("   Condition: arr[deque.peekLast()] <= arr[i]");
        System.out.println("   Reason: Discard smaller older elements because the larger new element dominates them.");
        System.out.println("2. Exercise 5 (Minimum):");
        System.out.println("   Condition: arr[deque.peekLast()] >= arr[i]");
        System.out.println("   Reason: Discard larger older elements because the smaller new element dominates them.");
        System.out.println("3. Both algorithms achieve optimal O(N) time and O(k) space complexity.");
    }
}
