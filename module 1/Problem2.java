
import java.util.Arrays;


/**
 * Problem 2: Reverse an Array In-Place.
 * 
 * Objective:
 * Reverse the order of elements in an array without allocating a second array,
 * using the two-pointer technique.
 * 
 * Complexity:
 * - Time: O(n) — approximately n/2 swaps are performed.
 * - Space: O(1) — only one temporary swap variable is used.
 */
public class Problem2 {


    static void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] arr = { 10, 20, 30, 40, 50 };

        System.out.println("Before: " + Arrays.toString(arr));
        reverse(arr);
        System.out.println("After:  " + Arrays.toString(arr));
    }
}
