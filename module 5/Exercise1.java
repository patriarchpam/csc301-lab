// Import Map utilities
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Exercise 1: Most Frequent Element (Mode) Detection & Map Ordering Comparison.
 * 
 * Objective:
 * 1. Modify the frequency counter to report which element occurs MOST frequently (and how many times).
 * 2. Formulate and document a policy for handling ties when multiple elements share the highest count.
 * 3. Compare HashMap with LinkedHashMap to observe and explain how the iteration order changes.
 * 
 * Tie-Handling Policy:
 * When two or more distinct elements tie for the maximum frequency, all tied
 * candidates are preserved and reported as co-modes (multimodal distribution).
 * This ensures no data is lost or arbitrarily favored.
 * 
 * HashMap vs LinkedHashMap:
 * - HashMap: Key placement depends solely on key.hashCode() and bucket count.
 *   Iteration order is non-deterministic and does not match array appearance.
 * - LinkedHashMap: Maintains an internal doubly-linked list through all entries.
 *   Iteration order strictly preserves the order in which keys were first inserted.
 */
public class Exercise1 {

    // Analyzes frequencies, finds the mode(s), and demonstrates map iteration order
    static void analyzeFrequencies(int[] arr) {
        System.out.println("Input Array: " + Arrays.toString(arr));

        // 1. Unordered HashMap analysis
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int v : arr) {
            hashMap.put(v, hashMap.getOrDefault(v, 0) + 1);
        }

        System.out.println("\n[1] Frequency counts using standard HashMap (Unordered / Hash-bucket order):");
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println("    Key: " + entry.getKey() + " -> Frequency: " + entry.getValue());
        }

        // 2. Order-preserving LinkedHashMap analysis
        Map<Integer, Integer> linkedMap = new LinkedHashMap<>();
        for (int v : arr) {
            linkedMap.put(v, linkedMap.getOrDefault(v, 0) + 1);
        }

        System.out.println("\n[2] Frequency counts using LinkedHashMap (Preserves First-Seen Insertion Order):");
        for (Map.Entry<Integer, Integer> entry : linkedMap.entrySet()) {
            System.out.println("    Key: " + entry.getKey() + " -> Frequency: " + entry.getValue());
        }

        // 3. Find Most Frequent Element(s) with tie resolution
        int maxCount = 0;
        for (int count : linkedMap.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        List<Integer> modes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : linkedMap.entrySet()) {
            if (entry.getValue() == maxCount) {
                modes.add(entry.getKey());
            }
        }

        System.out.println("\n[3] Mode (Most Frequent Element) Analysis:");
        System.out.println("    Maximum Frequency: " + maxCount);
        if (modes.size() == 1) {
            System.out.println("    Single Mode: Element " + modes.get(0) + " occurs " + maxCount + " times.");
        } else {
            System.out.println("    Tie Detected! Co-Modes: Elements " + modes + " each occur " + maxCount + " times.");
            System.out.println("    Tie Policy: Documented and preserved all tied elements without arbitrary truncation.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 1: Mode Detection & HashMap vs LinkedHashMap Ordering           ");
        System.out.println("==========================================================================");

        // Test Case 1: Clear single mode (4 occurs 3 times)
        System.out.println("\n--- Test Case 1: Single Highest Frequency ---");
        int[] arr1 = {4, 7, 4, 2, 7, 4};
        analyzeFrequencies(arr1);

        // Test Case 2: Tie between elements 10 and 20 (both occur 3 times)
        System.out.println("\n--- Test Case 2: Tie Between Multiple Elements ---");
        int[] arr2 = {10, 20, 10, 30, 20, 10, 20};
        analyzeFrequencies(arr2);

        System.out.println("\n=== Key Observation on Map Iteration ===");
        System.out.println("Notice that in Test Case 1:");
        System.out.println("- HashMap outputs key '2' first because of its internal bucket distribution.");
        System.out.println("- LinkedHashMap outputs '4', then '7', then '2', matching the exact order they appeared in arr.");
    }
}
