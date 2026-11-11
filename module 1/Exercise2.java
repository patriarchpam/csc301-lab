import java.util.Arrays;

/**
 * Exercise 2: Recursive In-Place Array Reversal.
 * 
 * Objective:
 * Rewrite the array reversal function recursively instead of iteratively
 * (reverse the sub-array between left + 1 and right - 1 after swapping the
 * outer pair). State the new space complexity, accounting for the recursion
 * call stack.
 * 
 * Complexity Analysis:
 * - Time Complexity: O(n) — performs n/2 swaps across n/2 recursive calls.
 * - Space Complexity: O(n) — unlike the iterative version which uses O(1) space,
 *   the recursive version uses O(n/2) = O(n) auxiliary space on the call stack
 *   because each recursive call adds a stack frame until left >= right.
 */
public class Exercise2 {

    static void reverseRecursive(int[] arr, int left, int right) {
        // Base case: pointers have met or crossed
        if (left >= right) {
            return;
        }

        // Swap the outer pair at left and right
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;

        // Recursively reverse the inner subarray
        reverseRecursive(arr, left + 1, right - 1);
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};

        System.out.println("Before: " + Arrays.toString(arr));
        reverseRecursive(arr, 0, arr.length - 1);
        System.out.println("After:  " + Arrays.toString(arr));
        System.out.println("Space Complexity: O(n) due to O(n/2) recursion stack frames.");
    }
}

