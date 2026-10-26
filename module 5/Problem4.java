// Import collections
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem 4: Group Anagrams Together From a List of Strings.
 * 
 * Objective:
 * Partition a list of words into groups, where each group contains all words
 * that are anagrams of one another.
 * 
 * Theory / Mechanism:
 * - Every anagram of a word produces the EXACT same character sequence when sorted.
 *   For example, "eat", "tea", and "ate" all sort to "aet".
 * - That sorted string serves as a canonical hash KEY in our map.
 * - The map's value is a dynamic list holding all original words that sort to that key.
 * - A naive pairwise comparison would cost O(n^2 * m); the hash-map grouping
 *   approach processes all words in a single pass of O(n * m log m).
 * 
 * Complexity:
 * - Time: O(n * m log m), where n is the number of words and m is the maximum word length.
 *   Sorting each word takes O(m log m).
 * - Space: O(n * m) to store all words and canonical keys in the hash map.
 */
public class Problem4 {

    // Groups words into anagram buckets using character sorting
    static List<List<String>> groupAnagrams(String[] words) {
        // Map from sorted canonical string -> list of original anagram words
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {
            // Convert word to char array and sort its letters
            char[] letters = word.toCharArray();
            Arrays.sort(letters);

            // Construct canonical key from sorted characters
            String key = new String(letters);

            // If key does not exist yet, create a new list for this family
            groups.putIfAbsent(key, new ArrayList<>());

            // Add the original word to the corresponding anagram group
            groups.get(key).add(word);
        }

        // Return all grouped lists as a combined list
        return new ArrayList<>(groups.values());
    }

    // Main method to test anagram grouping
    public static void main(String[] args) {
        System.out.println("=== Testing Group Anagrams (Problem 4) ===");

        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};

        System.out.println("Input: " + Arrays.toString(words));
        System.out.println("Grouped anagrams:");

        for (List<String> group : groupAnagrams(words)) {
            System.out.println("  " + group);
        }
    }
}
