import java.util.PriorityQueue;
import java.util.Arrays;

/**
 * Problem 3: Find the k Largest Elements in an Array Using a Heap.
 * 
 * Objective:
 * Identify the k largest values in an array of n elements without fully sorting
 * the array, using a min-heap of bounded size k.
 * 
 * Complexity:
 * - Time: O(n log k) — each of the n elements triggers at most one O(log k) heap operation.
 * - Space: O(k) — the min-heap holds at most k elements at any point.
 */
public class Problem3 {

    static int[] kLargest(int[] arr, int k) {
        // a MIN-heap of size at most k: its root is always the SMALLEST
        // of the k largest values seen so far
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int value : arr) {
            if (minHeap.size() < k) {
                minHeap.offer(value);
            } else if (value > minHeap.peek()) {
                minHeap.poll();       // discard the current smallest of the k
                minHeap.offer(value);
            }
            // otherwise: value is too small to be in the top k, discard it
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();   // extract in ascending order
        }
        return result;   // result is now sorted descending
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 5, 12, 2, 11, 9, 7};
        int k = 4;

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println(k + " largest elements: "
                           + Arrays.toString(kLargest(arr, k)));
    }
}
