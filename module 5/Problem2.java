// Import Map interface and HashMap class
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 2: Check if Two Strings Are Anagrams Using a Hash Map.
 * 
 * Objective:
 * Determine whether two strings contain identical characters with identical
 * frequencies (differing only in order) using a single-map build-then-cancel approach.
 * 
 * Theory / Mechanism:
 * - A single hash map counts characters:
 *   1. Scan string 'a', incrementing character frequencies in the map.
 *   2. Scan string 'b', decrementing character frequencies.
 *   3. If any character in 'b' is missing, strings cannot be anagrams -> return false.
 *   4. When a count drops to zero, the key is removed from the map.
 *   5. If both strings are valid anagrams, all counts cancel out and the map is empty.
 * - Why not just compare sets of characters?
 *   Comparing sets would wrongly identify "aab" and "abb" as anagrams because both contain
 *   {'a', 'b'}. Frequency counting correctly catches the quantity mismatch.
 * 
 * Complexity:
 * - Time: O(n) where n is the length of the strings
 * - Space: O(k) where k is the number of distinct characters (O(1) for fixed alphabet)
 */
public class Problem2 {

    // Returns true if strings a and b are exact anagrams
    static boolean areAnagrams(String a, String b) {
        // Quick rejection: if lengths differ, they cannot be anagrams
        if (a.length() != b.length()) return false;

        // Single frequency map for build-and-cancel matching
        Map<Character, Integer> freq = new HashMap<>();

        // Phase 1: Count character frequencies in the first string
        for (char ch : a.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        // Phase 2: Subtract character frequencies using the second string
        for (char ch : b.toCharArray()) {
            // If character does not exist in the map, string 'b' contains an alien character
            if (!freq.containsKey(ch)) return false;

            // Decrement frequency count
            freq.put(ch, freq.get(ch) - 1);

            // Clean up: remove the key once its count reaches zero
            if (freq.get(ch) == 0) freq.remove(ch);
        }

        // An empty map indicates perfectly balanced character counts across both strings
        return freq.isEmpty();
    }

    // Main method to test anagram evaluation
    public static void main(String[] args) {
        System.out.println("=== Testing Anagram Check (Problem 2) ===");

        System.out.println("listen/silent -> " + areAnagrams("listen", "silent"));
        System.out.println("hello/world   -> " + areAnagrams("hello", "world"));
        System.out.println("aab/abb       -> " + areAnagrams("aab", "abb"));
    }
}
