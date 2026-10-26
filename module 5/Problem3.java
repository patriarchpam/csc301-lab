// Import Map interface and HashMap
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 3: Find the First Non-Repeating Element in an Array.
 * 
 * Objective:
 * Identify the first element (in original left-to-right array order) that
 * occurs exactly once, using a two-pass hash map approach.
 * 
 * Theory / Mechanism:
 * - This problem combines two pieces of information:
 *   1. Frequency: How often each value occurs (efficiently tracked by a HashMap).
 *   2. Order: The chronological sequence of appearance (provided by array traversal).
 * - Because HashMap does NOT maintain ordering, we execute two passes:
 *   - PASS 1: Accumulate occurrence counts for all elements into the HashMap: O(n).
 *   - PASS 2: Traverse the array a second time in original left-to-right order.
 *             The first element with map.get(value) == 1 is our answer: O(n).
 * - If no element has a frequency of 1, return sentinel -1.
 * 
 * Complexity:
 * - Time: O(n) total (two sequential passes: n + n = 2n = O(n))
 * - Space: O(k) where k is the number of distinct elements
 */
public class Problem3 {

    // Finds the first unique element using two passes
    static int findFirstNonRepeating(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();

        // PASS 1: Count how many times each value occurs
        for (int value : arr) {
            freq.put(value, freq.getOrDefault(value, 0) + 1);
        }

        // PASS 2: Re-scan in ORIGINAL order and return first value with count 1
        for (int value : arr) {
            if (freq.get(value) == 1) {
                return value; // Found the first unique element in original order
            }
        }

        return -1; // Sentinel value indicating every element repeats
    }

    // Main method to test sample arrays
    public static void main(String[] args) {
        System.out.println("=== Testing First Non-Repeating Element (Problem 3) ===");

        int[] arr = {9, 4, 9, 6, 4, 8};

        System.out.print("Array: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();

        int result = findFirstNonRepeating(arr);
        System.out.println("First non-repeating element: " + result);

        int[] allRepeat = {5, 5, 3, 3};
        System.out.println("All-repeating array result: " +
                            findFirstNonRepeating(allRepeat));
    }
}
