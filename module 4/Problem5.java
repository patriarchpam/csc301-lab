// Import Java's double-ended queue collections
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Problem 5: Implement a Sliding Window Maximum Using a Monotonic Deque.
 * 
 * Objective:
 * Compute the maximum value within every contiguous subarray window of size k,
 * as the window slides from the start to the end of an array, in overall linear O(n) time.
 * 
 * Theory / Mechanism:
 * - A naive approach inspects all k elements for each of the (n - k + 1) windows, costing O(n*k).
 * - A double-ended queue (deque) achieves O(n) time by maintaining candidate INDICES
 *   in monotonically decreasing order of their corresponding array values.
 * - Before inserting index i, any indices at the BACK of the deque whose values are
 *   <= arr[i] are removed because they can never become the maximum as long as arr[i]
 *   is in the window.
 * - The FRONT of the deque always holds the index of the maximum element in the current window.
 * - If the index at the FRONT slides out of the window (i.e. front == i - k), it is polled.
 * 
 * Complexity:
 * - Time: O(n) total across the entire array (each index is inserted once and removed at most once)
 * - Space: O(k) auxiliary storage for the deque
 */
public class Problem5 {

    // Computes the maximum for each sliding window of size k
    static int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;

        // Base guard check for empty array or invalid window
        if (n == 0 || k <= 0) return new int[0];

        // Total number of sliding windows is n - k + 1
        int[] result = new int[n - k + 1];

        // Deque to store array indices in decreasing order of corresponding values
        Deque<Integer> deque = new ArrayDeque<>();

        // Iterate through each index of the input array
        for (int i = 0; i < n; i++) {
            // Remove indices whose values are smaller than or equal to arr[i];
            // they can never be the window maximum from now on
            while (!deque.isEmpty() && arr[deque.peekLast()] <= arr[i]) {
                deque.pollLast();
            }

            // Add the current index at the back of the deque
            deque.addLast(i);

            // Remove the front index if it has fallen outside the current window boundaries
            if (deque.peekFirst() == i - k) {
                deque.pollFirst();
            }

            // Once the first full window has formed (i >= k - 1), record the window maximum
            if (i >= k - 1) {
                result[i - k + 1] = arr[deque.peekFirst()];
            }
        }

        return result;
    }

    // Main method to test and demonstrate sliding window maximum
    public static void main(String[] args) {
        System.out.println("=== Testing Sliding Window Maximum (Problem 5) ===");

        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;

        int[] result = maxSlidingWindow(arr, k);

        System.out.print("Array: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println("\nWindow size k = " + k);

        System.out.print("Maximums: ");
        for (int v : result) System.out.print(v + " ");
        System.out.println();
    }
}
