import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Arrays;

/**
 * Exercise 3: Find the k Smallest Elements in an Array Using a Max-Heap of Size k.
 * 
 * Objective:
 * Adapt Problem 3 to find the k SMALLEST elements instead, using a MAX-heap of
 * size k rather than a min-heap (mirroring the logic exactly). Test the
 * adaptation on the same array {3, 1, 5, 12, 2, 11, 9, 7} with k = 3 and state
 * the expected result before running it to confirm.
 * 
 * Why a MAX-Heap is Used for the k SMALLEST Elements:
 * To keep the k smallest values seen so far, we need O(1) access to the LARGEST
 * of those k candidates (the threshold value) so we can check whether an
 * incoming number is even smaller and should evict it. A max-heap of size k
 * keeps that largest candidate at its root (`maxHeap.peek()`).
 * 
 * Expected Result for {3, 1, 5, 12, 2, 11, 9, 7} with k = 3:
 * The 3 smallest elements in the array are 1, 2, and 3 -> [1, 2, 3].
 */
public class Exercise3 {

    static int[] kSmallest(int[] arr, int k) {
        // A MAX-heap of size at most k: its root is always the LARGEST
        // of the k smallest values seen so far
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int value : arr) {
            if (maxHeap.size() < k) {
                maxHeap.offer(value);
            } else if (value < maxHeap.peek()) {
                maxHeap.poll();       // evict the current largest of the k smallest
                maxHeap.offer(value);
            }
            // otherwise: value >= maxHeap.peek(), so it is too large to be in the k smallest
        }

        // Extract from max-heap (largest to smallest) into result from back to front
        // so that the final array is ordered ascending: [smallest ... k-th smallest]
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 12, 2, 11, 9, 7};
        int k = 3;

        System.out.println("Array:                       " + Arrays.toString(arr));
        System.out.println("Expected " + k + " smallest (k=3):   [1, 2, 3]");
        System.out.println("Computed " + k + " smallest (k=3):   "
                           + Arrays.toString(kSmallest(arr, k)));
    }
}
