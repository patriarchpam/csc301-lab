import java.util.PriorityQueue;
import java.util.Collections;

/**
 * Exercise 5: Running Median Data Structure With Dynamic Element Removal (removeNumber).
 * 
 * Objective:
 * Extend Problem 5 with a removeNumber(int num) method that removes a previously
 * added number and correctly restores both the heap-size balance and the median.
 * Explain why this is significantly harder than addNumber(), given that Java's
 * PriorityQueue only supports O(n) arbitrary-element removal rather than O(log n).
 * 
 * Why removeNumber(int num) Is Harder Than addNumber(int num):
 * 1. Lack of Subtree Search Ordering (O(n) Lookup):
 *    Unlike a Binary Search Tree, a binary heap does NOT partition elements into
 *    "smaller on left, larger on right". Knowing a target value `num` only tells
 *    us which of the two heaps (`low` vs `high`) to search (by comparing `num <= low.peek()`),
 *    but inside that heap, finding `num`'s array index requires a linear scan of up
 *    to n/2 elements. Thus `PriorityQueue.remove(Object)` runs in O(n) time (O(n) linear
 *    scan + O(log n) sift), whereas `addNumber()` only touches the root/leaf in O(log n).
 * 2. Two-Direction Rebalancing:
 *    Removing from `low` can make `low.size() < high.size()`, requiring us to pull
 *    `high.poll()` into `low`. Conversely, removing from `high` can make
 *    `low.size() > high.size() + 1`, requiring us to push `low.poll()` into `high`.
 * (Note: In production systems needing O(log n) arbitrary removal, one either
 * uses two balanced BSTs / TreeMaps with frequency counts, an indexed priority
 * queue, or lazy deletion with a hash map of pending removals.)
 */
public class Exercise5 {

    static PriorityQueue<Integer> low =
        new PriorityQueue<>(Collections.reverseOrder());
    static PriorityQueue<Integer> high = new PriorityQueue<>();

    static void addNumber(int num) {
        low.offer(num);
        high.offer(low.poll());

        if (high.size() > low.size()) {
            low.offer(high.poll());
        }
    }

    // Removes a single occurrence of 'num' and restores the size invariant:
    // low.size() == high.size() OR low.size() == high.size() + 1
    static boolean removeNumber(int num) {
        if (low.isEmpty() && high.isEmpty()) {
            return false;
        }

        boolean removed;
        // Determine which heap holds 'num' by comparing against low's maximum
        if (!low.isEmpty() && num <= low.peek()) {
            removed = low.remove(num);   // O(n) linear search + O(log n) heap fix
        } else {
            removed = high.remove(num);  // O(n) linear search + O(log n) heap fix
        }

        if (!removed) {
            return false; // num was not present in the stream
        }

        // Rebalance heap sizes so that 0 <= low.size() - high.size() <= 1
        if (high.size() > low.size()) {
            low.offer(high.poll());
        } else if (low.size() > high.size() + 1) {
            high.offer(low.poll());
        }

        return true;
    }

    static double getMedian() {
        if (low.isEmpty()) {
            throw new IllegalStateException("No elements in stream");
        }
        if (low.size() > high.size()) {
            return low.peek();
        }
        return (low.peek() + high.peek()) / 2.0;
    }

    public static void main(String[] args) {
        int[] stream = {5, 15, 1, 3, 8, 7, 9, 2};

        System.out.println("=== Phase 1: Adding Stream Elements ===");
        for (int num : stream) {
            addNumber(num);
            System.out.println("add(" + num + ") -> median = " + getMedian()
                    + "  [low=" + low + ", high=" + high + "]");
        }

        System.out.println("\n=== Phase 2: Removing Elements and Recomputing Median ===");
        // Active sorted elements before removals: [1, 2, 3, 5, 7, 8, 9, 15] (median = 6.0)
        int[] toRemove = {2, 15, 5, 7};
        for (int num : toRemove) {
            boolean ok = removeNumber(num);
            System.out.println("remove(" + num + ") [removed=" + ok + "] -> median = "
                    + getMedian() + "  [low=" + low + ", high=" + high + "]");
        }

        System.out.println("\n=== Why removeNumber() Is Harder Than addNumber() ===");
        System.out.println("1. O(n) Lookup in Java's PriorityQueue: A heap only orders parent vs. children,");
        System.out.println("   so locating an arbitrary element inside low or high requires an O(n) linear scan");
        System.out.println("   before the O(log n) sift, making removeNumber() O(n) instead of O(log n).");
        System.out.println("2. Bidirectional Rebalancing: Removal can shrink either low or high, requiring");
        System.out.println("   rebalancing in both directions (high -> low if high.size() > low.size(), or");
        System.out.println("   low -> high if low.size() > high.size() + 1).");
    }
}
