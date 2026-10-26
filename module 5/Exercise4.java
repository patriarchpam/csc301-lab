// Import Java collections
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exercise 4: Anagram Grouping Using Frequency-Count Signatures (No Sorting).
 * 
 * Objective:
 * Group anagrams using a frequency-count key signature (e.g. "a1e1t1") instead
 * of sorting the characters of each word.
 * 
 * Mechanism:
 * 1. Allocate an integer frequency array of size 26 for each word: int[26].
 * 2. Count the occurrences of each character in O(m) time: count[ch - 'a']++.
 * 3. Serialize the counts into a unique canonical signature string, such as:
 *    "eat" -> "a1e1t1"
 *    "tea" -> "a1e1t1"
 *    "ate" -> "a1e1t1"
 * 4. Use this signature string directly as the HashMap bucket key.
 * 
 * Time Complexity Comparison:
 * - Sorting Approach (Problem 4): O(n * m log m)
 * - Frequency Signature Approach (Exercise 4): O(n * (m + Σ)), where Σ = 26 (alphabet size).
 *   Since Σ is a constant 26, the time complexity simplifies to O(n * m) — purely linear!
 * 
 * Performance Trade-Off Analysis:
 * When is the frequency signature FASTER?
 * - Long Words: When m is large (e.g., m = 100 or 1,000+ characters), O(m) is dramatically
 *   faster than O(m log m) comparison-based sorting.
 * - Small Alphabet: When alphabet size Σ is compact (e.g., 26 English letters, 4 DNA bases).
 * 
 * When is Sorting FASTER or PREFERRED?
 * - Very Short Words: For words with 2 to 4 letters, sorting costs negligible CPU cycles,
 *   while allocating a 26-element count array and building a string has small constant overhead.
 * - Massive Alphabets: If the alphabet is full Unicode (UTF-16 with 65,536+ symbols),
 *   a fixed frequency array is impractical, making character sorting (or sparse maps) far better.
 */
public class Exercise4 {

    // Generates a canonical frequency signature (e.g., "a1e1t1") for a lowercase word
    static String getFrequencySignature(String word) {
        int[] counts = new int[26];

        // Step 1: Count character occurrences in O(m) time
        for (char ch : word.toCharArray()) {
            counts[ch - 'a']++;
        }

        // Step 2: Build deterministic signature string in O(26) = O(1) time
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                sb.append((char) ('a' + i)).append(counts[i]);
            }
        }

        return sb.toString();
    }

    // Groups words using the frequency signature method
    static List<List<String>> groupAnagramsByFrequency(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {
            // Generate signature without any sorting: O(m)
            String signature = getFrequencySignature(word);

            groups.putIfAbsent(signature, new ArrayList<>());
            groups.get(signature).add(word);
        }

        return new ArrayList<>(groups.values());
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 4: Group Anagrams via Frequency Signature (O(n * m) Linear Time) ");
        System.out.println("==========================================================================");

        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};

        System.out.println("Input Words: " + Arrays.toString(words));
        System.out.println("\nGenerated Frequency Signatures for Each Word:");
        for (String w : words) {
            System.out.printf("  Word: %-5s -> Frequency Key: %s%n", w, getFrequencySignature(w));
        }

        List<List<String>> grouped = groupAnagramsByFrequency(words);

        System.out.println("\nGrouped Anagrams Result:");
        for (List<String> group : grouped) {
            System.out.println("  " + group);
        }

        System.out.println("\n=== Complexity & Trade-Off Summary ===");
        System.out.println("1. Time Complexity: O(n * (m + Σ)) = O(n * m) linear time in word length.");
        System.out.println("2. Sorting Elimination: No Arrays.sort() needed, avoiding O(m log m) comparisons.");
        System.out.println("3. Sweet Spot: Optimal when words are long and alphabet is bounded (e.g. English, DNA).");
    }
}
