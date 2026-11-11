import java.util.Arrays;

/**
 * Exercise 4: Rotate an Array to the RIGHT by k Positions (In-Place).
 * 
 * Objective:
 * Adapt the Reversal Algorithm from Problem 4 to rotate the array to the RIGHT
 * by k positions instead of left. Verify using the same test array
 * {1, 2, 3, 4, 5, 6, 7} and k = 3.
 * 
 * Explanation:
 * Rotating right by k positions moves the last k elements to the front.
 * Equivalently:
 * - Method A: Reverse the entire array (0 to n-1), then reverse the first k
 *   elements (0 to k-1), then reverse the remaining n-k elements (k to n-1).
 * - Method B (Hint): A right rotation by k is identical to a left rotation by (n - k).
 */
public class Exercise4 {

    static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    static void rotateRight(int[] arr, int k) {
        int n = arr.length;
        k = k % n;                    // handle k >= n
        if (k == 0) return;

        // Right rotation by k using 3 reversals:
        reverse(arr, 0, n - 1);       // Step 1: reverse the whole array
        reverse(arr, 0, k - 1);       // Step 2: reverse first k elements
        reverse(arr, k, n - 1);       // Step 3: reverse remaining n - k elements
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;

        System.out.println("Original:            " + Arrays.toString(arr));
        rotateRight(arr, k);
        System.out.println("Rotated right by " + k + ":  " + Arrays.toString(arr));
        // Expected: [5, 6, 7, 1, 2, 3, 4]
    }
}

