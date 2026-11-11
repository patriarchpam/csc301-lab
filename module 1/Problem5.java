import java.util.Arrays;

/**
 * Problem 5: Maximum Subarray Sum (Kadane's Algorithm).
 * 
 * Objective:
 * Find the contiguous subarray within a one-dimensional array (containing at
 * least one number) that has the largest possible sum, in a single pass.
 * 
 * Complexity:
 * - Time: O(n) — single pass through the array with O(1) work per element.
 * - Space: O(1) — only a fixed number of tracking variables are needed.
 */
public class Problem5 {

    static int start, end;

    static int maxSubArraySum(int[] arr) {
        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];
        int tempStart = 0;
        start = 0;
        end = 0;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxEndingHere + arr[i]) {
                maxEndingHere = arr[i];
                tempStart = i;
            } else {
                maxEndingHere = maxEndingHere + arr[i];
            }

            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }
        return maxSoFar;
    }
    public static void main(String[] args) {
        int[] arr = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };

        int maxSum = maxSubArraySum(arr);

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Maximum Subarray Sum = " + maxSum);
        System.out.print("Subarray: ");
        for (int i = start; i <= end; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}
