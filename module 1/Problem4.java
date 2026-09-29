import java.util.HashSet;
 
public class Problem4 {
 
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
        if (pairCount == 0) System.out.println("  No pairs found.");
    }
 
    public static void main(String[] args) {
        int[] arr = {2, 7, 11, 15, 1, 8};
        int target = 9;
 
        findPairs(arr, target);
    }
}
 