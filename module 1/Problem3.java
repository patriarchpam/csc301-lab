import java.util.HashSet;

/**
 * Problem 3: Find All Pairs in an Array That Sum to a Given Target.
 * 
 * Objective:
 * Identify every pair of elements (a, b) in an array such that a + b equals a
 * specified target value, using an efficient single-pass HashSet approach.
 * 
 * Complexity:
 * - Time: O(n) average case, using O(1) average-time hash-set lookups.
 * - Space: O(n) — for storing seen elements in the hash set.
 */
public class Problem3 {

    static void findPairs(int[] arr, int target) {
        HashSet<Integer> seen = new HashSet<>();
        int pairCount = 0;

        System.out.println("Pairs that sum to " + target + ":");
        for (int x : arr) {
            int complement = target - x;
            if (seen.contains(complement)) {
                System.out.println("  (" + complement + ", " + x + ")");
                pairCount++;
            }
            seen.add(x);
        }
        if (pairCount == 0)
            System.out.println("  No pairs found.");
    }

    public static void main(String[] args) {
        int[] arr = { 2, 7, 11, 15, 1, 8 };
        int target = 9;

        findPairs(arr, target);
    }
}
