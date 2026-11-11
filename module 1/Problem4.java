import java.util.Arrays;

/**
 * Problem 4: Rotate an Array by k Positions (In-Place).
 * 
 * Objective:
 * Shift every element of an array k positions to the left (with wrap-around)
 * without using a second array, using the three-step Reversal Algorithm.
 * 
 * Complexity:
 * - Time: O(n) — three sequential reversal calls touching at most n elements.
 * - Space: O(1) — all reversals happen in place.
 */
public class Problem4 {

    static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    static void rotateLeft(int[] arr, int k) {
        int n = arr.length;
        k = k % n; // handle k > n
        reverse(arr, 0, k - 1); // Step 1: reverse first k elements
        reverse(arr, k, n - 1); // Step 2: reverse remaining elements
        reverse(arr, 0, n - 1); // Step 3: reverse the whole array
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 6, 7 };
        int k = 3;

        System.out.println("Original: " + Arrays.toString(arr));
        rotateLeft(arr, k);
        System.out.println("Rotated left by " + k + ": " + Arrays.toString(arr));
    }
}
