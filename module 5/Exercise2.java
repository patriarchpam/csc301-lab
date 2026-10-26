// Import Map utilities
import java.util.HashMap;
import java.util.Map;

/**
 * Exercise 2: Case-Insensitive, Punctuation-Agnostic Phrase Anagram Checker.
 * 
 * Objective:
 * Extend the anagram checker to correctly identify multi-word phrases as anagrams
 * regardless of casing, spacing, and punctuation (e.g. "Dormitory" and "Dirty Room").
 * 
 * Preprocessing Rationale:
 * Why must preprocessing happen BEFORE frequency counting?
 * 1. String Length Mismatch: "Dormitory" has 9 chars; "Dirty Room" has 10 chars (due to space).
 *    Without preprocessing, an early length check or space count fails immediately.
 * 2. Character Case Mismatch: Uppercase 'D' (ASCII 68) and lowercase 'd' (ASCII 100)
 *    are distinct Character objects with distinct hash codes. They would enter
 *    different map buckets and never cancel each other out.
 * 3. Non-letter Symbols: Spaces, hyphens, and punctuation symbols (' ', ',', '!')
 *    do not contribute to word anagrams and must be filtered out.
 * 
 * Preprocessing Pipeline:
 * str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase()
 */
public class Exercise2 {

    // Preprocessing step: strips whitespace/punctuation and converts to lowercase
    static String normalize(String str) {
        if (str == null) return "";
        // Keep only alphanumeric characters and normalize to lowercase
        return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    // Evaluates whether two phrases are anagrams after normalization
    static boolean arePhraseAnagrams(String phrase1, String phrase2) {
        // Step 1: Preprocess both input strings
        String norm1 = normalize(phrase1);
        String norm2 = normalize(phrase2);

        // Step 2: Quick length rejection on normalized representations
        if (norm1.length() != norm2.length()) {
            return false;
        }

        // Step 3: Single-map build-then-cancel frequency algorithm
        Map<Character, Integer> freq = new HashMap<>();

        // Count characters from first normalized phrase
        for (char ch : norm1.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        // Subtract characters from second normalized phrase
        for (char ch : norm2.toCharArray()) {
            if (!freq.containsKey(ch)) return false;
            freq.put(ch, freq.get(ch) - 1);
            if (freq.get(ch) == 0) {
                freq.remove(ch);
            }
        }

        // Return true if perfectly balanced
        return freq.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 2: Case-Insensitive & Whitespace-Agnostic Phrase Anagrams       ");
        System.out.println("==========================================================================");

        String[][] testCases = {
            {"Dormitory", "Dirty Room"},
            {"Conversation", "Voices, rant on!"},
            {"Astronomer", "Moon starer"},
            {"The eyes", "They see"},
            {"A gentleman", "Elegant man"},
            {"Hello", "World"},
            {"Listen", "Silent!"},
            {"Apple", "Pabble"}
        };

        for (String[] test : testCases) {
            String p1 = test[0];
            String p2 = test[1];
            boolean result = arePhraseAnagrams(p1, p2);

            System.out.println("\nTesting: \"" + p1 + "\" vs \"" + p2 + "\"");
            System.out.println("  -> Normalized 1: \"" + normalize(p1) + "\"");
            System.out.println("  -> Normalized 2: \"" + normalize(p2) + "\"");
            System.out.println("  -> Are Anagrams? " + (result ? "TRUE (MATCH)" : "FALSE (NO MATCH)"));
        }

        System.out.println("\n=== Preprocessing Analysis Summary ===");
        System.out.println("1. Filtering: Removes all spaces, tabs, and punctuation so non-semantic chars are ignored.");
        System.out.println("2. Normalization: toLowerCase() unifies letter representations so 'D' == 'd'.");
        System.out.println("3. Placement: MUST occur before counting to ensure bucket keys and string lengths align.");
    }
}
