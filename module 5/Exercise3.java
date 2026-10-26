// Import Map utilities and Optional container
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;

/**
 * Exercise 3: Robust Type-Safe Result Handling Using java.util.Optional.
 * 
 * Objective:
 * Replace the error-prone primitive sentinel value (-1) with Java's Optional<Integer>
 * return type to eliminate ambiguity when the array actually contains -1 as a valid element.
 * 
 * The Sentinel Value Anti-Pattern vs Optional<T>:
 * - In Problem 3, the method returns -1 when no non-repeating element is found.
 * - Flaw: What happens if the input array is {-1, 5, 5, 8, 8}?
 *   Here, -1 is the legitimate, unique answer!
 *   However, the caller cannot tell whether -1 means "the answer is -1" or
 *   "no answer exists". This is a classic sentinel ambiguity / silent defect.
 * - Solution: java.util.Optional<Integer> explicitly models the presence or absence
 *   of a value.
 *   - Optional.of(-1)    -> Clearly indicates -1 was found as the valid answer.
 *   - Optional.empty()   -> Clearly indicates no unique element exists in the array.
 */
public class Exercise3 {

    // Returns an Optional containing the first unique element, or Optional.empty() if none exists
    static Optional<Integer> findFirstNonRepeating(int[] arr) {
        if (arr == null || arr.length == 0) {
            return Optional.empty();
        }

        Map<Integer, Integer> freq = new HashMap<>();

        // PASS 1: Accumulate frequencies
        for (int value : arr) {
            freq.put(value, freq.getOrDefault(value, 0) + 1);
        }

        // PASS 2: Traverse array in original order
        for (int value : arr) {
            if (freq.get(value) == 1) {
                // Wrap the found value in an Optional
                return Optional.of(value);
            }
        }

        // Return empty container when every element in the array repeats
        return Optional.empty();
    }

    // Helper method to execute and print test scenarios
    static void testArray(String testName, int[] arr) {
        System.out.println("\n--- " + testName + " ---");
        System.out.println("Array: " + Arrays.toString(arr));

        Optional<Integer> result = findFirstNonRepeating(arr);

        // Modern, type-safe handling using Optional API
        if (result.isPresent()) {
            System.out.println("Result: Found unique element -> " + result.get());
        } else {
            System.out.println("Result: No non-repeating element exists (Optional.empty).");
        }

        // Demonstrating functional ifPresentOrElse style (Java 9+)
        System.out.print("Handled via ifPresentOrElse: ");
        result.ifPresentOrElse(
            val -> System.out.println("Valid unique value: " + val),
            ()  -> System.out.println("All elements repeated.")
        );
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 3: Type-Safe Unique Element Search with Optional<Integer>       ");
        System.out.println("==========================================================================");

        // Test Case 1: Standard positive unique elements
        testArray("Test 1: Standard array with positive unique value", new int[]{9, 4, 9, 6, 4, 8});

        // Test Case 2: All elements repeat
        testArray("Test 2: All elements repeat (no answer)", new int[]{5, 5, 3, 3});

        // Test Case 3: Crucial edge case where -1 IS the unique element!
        testArray("Test 3: Array where -1 IS a legitimate unique element", new int[]{-1, 2, 2, 4, 4});

        // Test Case 4: Array where -1 itself repeats
        testArray("Test 4: Array where -1 repeats and another element is unique", new int[]{-1, -1, 7, 8, 8});

        System.out.println("\n=== Architectural Takeaways ===");
        System.out.println("1. Robustness: Optional<T> forces calling code to handle the 'empty' case explicitly,");
        System.out.println("   preventing accidental NullPointerExceptions or sentinel misinterpretations.");
        System.out.println("2. Clarity: The method signature Optional<Integer> cleanly communicates that an");
        System.out.println("   element might not exist, self-documenting the API.");
    }
}
