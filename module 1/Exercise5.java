import java.util.Arrays;

/**
 * Exercise 5: Kadane's Algorithm on All-Negative Arrays.
 * 
 * Objective:
 * Extend Problem 5 to test and handle an array where ALL elements are negative
 * (e.g., [-8, -3, -6, -2, -5]). Confirm that the program returns a valid
 * single-element subarray (-2) rather than 0, and explain why returning 0
 * would be incorrect.
 * 
 * Why Returning 0 Would Be Incorrect:
 * The problem requires finding a non-empty contiguous subarray (containing at
 * least one number). Returning 0 corresponds to the sum of an EMPTY subarray
 * (0 elements), which violates the problem specification when every element in
 * the array is negative. Because our implementation initializes `maxSoFar` and
 * `maxEndingHere` to `arr[0]` (rather than 0) and compares `arr[i]` against
 * `maxEndingHere + arr[i]`, it naturally picks the least negative element (-2).
 */
public class Exercise5 {

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
        int[] arr = {-8, -3, -6, -2, -5};

        int maxSum = maxSubArraySum(arr);

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Maximum Subarray Sum = " + maxSum);
        System.out.print("Subarray: ");
        for (int i = start; i <= end; i++) System.out.print(arr[i] + " ");
        System.out.println("(at index " + start + ")");
        System.out.println("\nExplanation: Returning 0 would represent an empty subarray, which is invalid");
        System.out.println("when the subarray must contain at least one element. The least negative");
        System.out.println("single element (-2) is the true maximum contiguous subarray.");
    }
}

