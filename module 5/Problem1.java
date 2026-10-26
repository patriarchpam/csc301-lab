// Import Java's Map interface and HashMap implementation
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 1: Count the Frequency of Each Element in an Array Using a Hash Map.
 * 
 * Objective:
 * Accumulate and display the occurrence count of each distinct value in an array
 * in a single O(n) pass using a hash table.
 * 
 * Theory / Mechanism:
 * - A hash table converts each key into a bucket index using key.hashCode().
 * - Each distinct array value acts as a KEY, and its frequency count acts as the VALUE.
 * - Using map.getOrDefault(value, 0) allows safe incrementing without an extra lookup
 *   or containsKey check.
 * - Unlike a naive nested-loop approach costing O(n^2), the hash map achieves
 *   linear O(n) time average performance.
 * 
 * Complexity:
 * - Time: O(n) average
 * - Space: O(k) where k is the number of distinct values in the array
 */
public class Problem1 {

    // Counts frequencies of elements in arr and prints the mapping
    static void countFrequency(int[] arr) {
        // Map to store value -> count mappings
        Map<Integer, Integer> freq = new HashMap<>();

        // Single pass over the array: O(n) time
        for (int value : arr) {
            // getOrDefault retrieves existing count or defaults to 0 if not yet seen
            freq.put(value, freq.getOrDefault(value, 0) + 1);
        }

        // Display results
        System.out.println("Element frequencies:");
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Main method to test frequency counting
    public static void main(String[] args) {
        System.out.println("=== Testing Frequency Count (Problem 1) ===");

        int[] arr = {4, 7, 4, 2, 7, 4};

        System.out.print("Array: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();

        countFrequency(arr);
    }
}
