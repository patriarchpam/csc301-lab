// Import required Java utilities
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 4: Streaming Processor with End-of-Stream Frequency Analysis.
 * 
 * Objective:
 * Extend the streaming first-non-repeating-character program to support a
 * "stream ended" query that reports whether AT LEAST ONE character in the entire
 * stream never repeated at all — even if it was not at the front of the candidate
 * queue at the time of stream completion.
 * 
 * Theoretical Insight:
 * The candidate queue is responsible for tracking CHRONOLOGICAL order and
 * giving O(1) amortised access to the FIRST non-repeating character in the active window.
 * However, the frequency map contains the complete, global multiset distribution of
 * all characters processed throughout the entire stream.
 * Therefore, to verify if at least one character never repeated at all, we only need
 * to inspect the frequency map's values: if any character has a count of exactly 1,
 * the answer is TRUE.
 */
public class Exercise4 {

    // Encapsulated stream processor class
    static class StreamProcessor {
        private Queue<Character> candidates;
        private Map<Character, Integer> freq;
        private StringBuilder streamHistory;

        // Constructor initializing empty stream buffers
        public StreamProcessor() {
            this.candidates = new LinkedList<>();
            this.freq = new HashMap<>();
            this.streamHistory = new StringBuilder();
        }

        // Ingests a new character from the stream
        public void processChar(char ch) {
            streamHistory.append(ch);
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
            candidates.add(ch);

            // Clean up repeated candidates from front
            while (!candidates.isEmpty() && freq.get(candidates.peek()) > 1) {
                candidates.poll();
            }

            char currentFirst = candidates.isEmpty() ? '#' : candidates.peek();
            System.out.printf("  Processed '%c' | Queue Front: %-4s | Stream so far: %s%n",
                              ch, (currentFirst == '#' ? "None" : String.valueOf(currentFirst)),
                              streamHistory.toString());
        }

        // Processes an entire string stream character by character
        public void processStream(String stream) {
            System.out.println("\n--- Processing stream: \"" + stream + "\" ---");
            for (char ch : stream.toCharArray()) {
                processChar(ch);
            }
        }

        // Stream Ended Query 1: Checks whether AT LEAST ONE character never repeated
        public boolean hasAtLeastOneNonRepeating() {
            // Traverse map values looking for any character with count == 1
            for (int count : freq.values()) {
                if (count == 1) {
                    return true;
                }
            }
            return false;
        }

        // Stream Ended Query 2: Retrieves all characters that occurred exactly once
        public List<Character> getAllUniqueCharacters() {
            List<Character> uniques = new ArrayList<>();
            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == 1) {
                    uniques.add(entry.getKey());
                }
            }
            return uniques;
        }

        // Generates a comprehensive summary report once the stream terminates
        public void reportStreamEndedSummary() {
            System.out.println("\n=== Stream Terminated: End-of-Stream Analysis ===");
            System.out.println("Full Stream Processed: \"" + streamHistory.toString() + "\"");
            System.out.println("Frequency Table: " + freq);

            boolean hasUnique = hasAtLeastOneNonRepeating();
            System.out.println("Did at least ONE character never repeat? " + (hasUnique ? "YES (true)" : "NO (false)"));

            if (hasUnique) {
                List<Character> uniques = getAllUniqueCharacters();
                System.out.println("Non-repeating character(s) found: " + uniques);
            } else {
                System.out.println("All characters in the stream repeated at least twice!");
            }
            System.out.println("=================================================");
        }
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 4: First Non-Repeating Stream Processor with End-of-Stream Query ");
        System.out.println("==========================================================================");

        // Test Case 1: Stream "aabc" (has non-repeating characters 'b' and 'c')
        StreamProcessor sp1 = new StreamProcessor();
        sp1.processStream("aabc");
        sp1.reportStreamEndedSummary();

        // Test Case 2: Stream "aabbcc" (every character repeats, expected: false)
        StreamProcessor sp2 = new StreamProcessor();
        sp2.processStream("aabbcc");
        sp2.reportStreamEndedSummary();

        // Test Case 3: Stream "teeter" ('t':2, 'e':3, 'r':1 -> 'r' is unique)
        StreamProcessor sp3 = new StreamProcessor();
        sp3.processStream("teeter");
        sp3.reportStreamEndedSummary();
    }
}
