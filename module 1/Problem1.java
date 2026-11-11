/**
 * Problem 1: Find the Maximum and Minimum Values in an Array.
 * 
 * Objective:
 * Scan a one-dimensional array once and determine its largest and smallest
 * elements without sorting it.
 * 
 * Complexity:
 * - Time: O(n) — the array is scanned exactly once.
 * - Space: O(1) — only two extra variables (max, min) are used.
 */
public class Problem1 {

    static int max, min;

    static void findMaxMin(int[] arr) {
        max = arr[0];
        min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 45, 3, 89, 27, 6, 49};

        findMaxMin(arr);

        System.out.print("Array: ");
        for (int v : arr) System.out.print(v + " ");
        System.out.println();
        System.out.println("Maximum = " + max);
        System.out.println("Minimum = " + min);
    }
}
