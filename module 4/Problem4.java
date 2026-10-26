// Import Java collections for queue and hash map implementations
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 4: Find the First Non-Repeating Character in a Stream Using a Queue.
 * 
 * Objective:
 * Process a stream of characters one at a time and, after each character arrives,
 * report the first character seen so far that has not yet repeated — without
 * rescanning the stream from the beginning at each step.
 * 
 * Mechanism:
 * - A FIFO Queue stores candidate characters in their order of first appearance.
 * - A HashMap records running frequencies for every character seen so far.
 * - Whenever the character at the front of the queue has frequency > 1, it is
 *   permanently popped from the front (since it can never again be non-repeating).
 * - After clearing repeated front candidates, the front of the queue holds the
 *   first non-repeating character (or 'None' / '#' if the queue is empty).
 * 
 * Complexity:
 * - Time: O(1) amortised per character (each character is enqueued once and dequeued at most once)
 * - Space: O(k) where k is the number of distinct characters in the alphabet (e.g. O(26))
 */
public class Problem4 {

    // Processes an incoming stream of characters and prints the current first non-repeating character
    static void processStream(String stream) {
        // Queue to hold candidate characters in chronological order
        Queue<Character> candidates = new LinkedList<>();

        // Map to keep track of occurrence count for each character
        Map<Character, Integer> freq = new HashMap<>();

        // Iterate through each character in the incoming character stream
        for (char ch : stream.toCharArray()) {
            // Update frequency count in the map
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);

            // Add the new character as a candidate to the rear of the queue
            candidates.add(ch);

            // Discard candidate characters from the front that have become repeated (count > 1)
            while (!candidates.isEmpty() && freq.get(candidates.peek()) > 1) {
                candidates.poll();
            }

            // If candidates queue is empty, no non-repeating character currently exists
            char result = candidates.isEmpty() ? '#' : candidates.peek();

            // Print the current status after processing character 'ch'
            System.out.println("After '" + ch + "': first non-repeating = " +
                                (result == '#' ? "None" : result));
        }
    }

    // Main method to test stream processing
    public static void main(String[] args) {
        System.out.println("=== Testing First Non-Repeating in Stream (Problem 4) ===");
        processStream("aabc");
    }
}
