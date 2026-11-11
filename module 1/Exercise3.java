import java.util.Arrays;
import java.util.HashSet;

/**
 * Exercise 3: Find All Unique Pairs in an Array That Sum to a Given Target.
 * 
 * Objective:
 * Modify Problem 3 to avoid printing the same pair twice when the array itself
 * contains duplicate values (e.g., array = [3, 3, 6] or [3, 3, 3] with target = 6
 * should report (3, 3) only once). Explain the fix.
 * 
 * Explanation of Fix:
 * We maintain a second HashSet (`printed`) that records canonical pair keys
 * (using the smaller value of the pair, `Math.min(x, complement)`). Since target
 * is fixed, knowing `min(x, complement)` uniquely identifies the unordered pair
 * `{x, complement}`. Before printing a matched pair, we check if `minVal` is
 * already in `printed`; if not, we print the pair and add `minVal` to `printed`.
 */
public class Exercise3 {

    static void findUniquePairs(int[] arr, int target) {
        HashSet<Integer> seen = new HashSet<>();
        HashSet<Integer> printedMinValues = new HashSet<>();
        int pairCount = 0;

        System.out.println("Array: " + Arrays.toString(arr) + ", Target: " + target);
        System.out.println("Unique pairs that sum to " + target + ":");
        for (int x : arr) {
            int complement = target - x;
            if (seen.contains(complement)) {
                int minVal = Math.min(x, complement);
                int maxVal = Math.max(x, complement);
                // Only print if this unique pair has not been printed yet
                if (!printedMinValues.contains(minVal)) {
                    System.out.println("  (" + minVal + ", " + maxVal + ")");
                    printedMinValues.add(minVal);
                    pairCount++;
                }
            }
            seen.add(x);
        }
        if (pairCount == 0) System.out.println("  No pairs found.");
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 3, 3, 6};
        findUniquePairs(arr1, 6);

        System.out.println();
        int[] arr2 = {2, 7, 2, 7, 1, 8, 1, 8};
        findUniquePairs(arr2, 9);
    }
}

