import java.util.Arrays;

/**
 * Exercise 1: Find the Maximum and Minimum Values and Their Indices in an Array.
 * 
 * Objective:
 * Modify Problem 1 to also return the INDEX at which the maximum and minimum
 * values occur. Test it with an array that contains duplicate maximum values
 * and explain, in a comment, which index the program returns.
 * 
 * Duplicate Maximum Index Behavior:
 * Because our comparison uses strictly greater-than (arr[i] > max), when a
 * subsequent element equals the current maximum (arr[i] == max), the condition
 * evaluates to false and maxIndex is NOT updated. Therefore, the program
 * returns the FIRST (earliest) index where the maximum value occurs.
 * (If we changed '>' to '>=', it would return the LAST index instead.)
 */
public class Exercise1 {

    static int max, min;
    static int maxIndex, minIndex;

    static void findMaxMinWithIndices(int[] arr) {
        max = arr[0];
        min = arr[0];
        maxIndex = 0;
        minIndex = 0;

        for (int i = 1; i < arr.length; i++) {
            // Strict '>' means duplicates do not overwrite maxIndex (keeps FIRST occurrence)
            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
            // Strict '<' means duplicates do not overwrite minIndex (keeps FIRST occurrence)
            if (arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1: Standard Array ===");
        int[] arr1 = {12, 45, 3, 89, 27, 6, 49};
        findMaxMinWithIndices(arr1);
        System.out.println("Array:   " + Arrays.toString(arr1));
        System.out.println("Maximum = " + max + " (at index " + maxIndex + ")");
        System.out.println("Minimum = " + min + " (at index " + minIndex + ")");

        System.out.println("\n=== Test 2: Array with Duplicate Maximum Values ===");
        // 89 appears at index 1 and again at index 4
        int[] arr2 = {12, 89, 3, 45, 89, 6, 89};
        findMaxMinWithIndices(arr2);
        System.out.println("Array:   " + Arrays.toString(arr2));
        System.out.println("Maximum = " + max + " (at index " + maxIndex + " -> first occurrence)");
        System.out.println("Minimum = " + min + " (at index " + minIndex + ")");
    }
}

