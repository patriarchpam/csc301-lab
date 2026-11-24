import java.util.PriorityQueue;
import java.util.Collections;

/**
 * Problem 5: Design a Data Structure to Find the Median of a Running Stream of Numbers.
 * 
 * Objective:
 * Maintain a running MEDIAN — recomputed after every new number arrives — over
 * a stream of numbers seen one at a time in O(log n) per insertion and O(1) per
 * median query, using a max-heap (`low`) for the smaller half and a min-heap
 * (`high`) for the larger half.
 * 
 * Complexity:
 * - Time: O(log n) per addNumber() call; O(1) per getMedian() call.
 * - Space: O(n) to store all numbers across the two heaps.
 */
public class Problem5 {

    // "low": SMALLER half of numbers seen (max-heap; root = its largest)
    static PriorityQueue<Integer> low =
        new PriorityQueue<>(Collections.reverseOrder());
    // "high": LARGER half of numbers seen (min-heap; root = its smallest)
    static PriorityQueue<Integer> high = new PriorityQueue<>();

    static void addNumber(int num) {
        low.offer(num);              // always insert into low first
        high.offer(low.poll());      // move low's largest into high

        // rebalance: low is allowed at most ONE more element than high
        if (high.size() > low.size()) {
            low.offer(high.poll());
        }
    }

    static double getMedian() {
        if (low.size() > high.size()) {
            return low.peek();                          // odd total count
        }
        return (low.peek() + high.peek()) / 2.0;        // even total count
    }

    public static void main(String[] args) {
        int[] stream = {5, 15, 1, 3, 8, 7, 9, 2};

        for (int num : stream) {
            addNumber(num);
            System.out.println("add(" + num + ") -> median = " + getMedian());
        }
    }
}
