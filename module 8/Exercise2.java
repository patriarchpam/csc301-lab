import java.util.Arrays;

/**
 * Exercise 2: Descending-Order Heap Sort Using a Min-Heap.
 * 
 * Objective:
 * Modify heapSort() to sort in DESCENDING order instead of ascending, using a
 * MIN-heap instead of a max-heap (rather than simply reversing the final
 * ascending result). State exactly which lines of the algorithm must change
 * and why the overall time complexity remains O(n log n).
 * 
 * Exact Lines That Change:
 * Only the two comparison lines inside heapifyDown() need to change their
 * inequality operator from '>' to '<' (and renaming 'largest' to 'smallest'
 * for clarity):
 *   1. `if (left < n && arr[left] > arr[largest])`   --> `if (left < n && arr[left] < arr[smallest])`
 *   2. `if (right < n && arr[right] > arr[largest])` --> `if (right < n && arr[right] < arr[smallest])`
 * 
 * Why Time Complexity Remains O(n log n):
 * Reversing the comparison direction from '>' to '<' simply turns the complete
 * binary tree into a min-heap of the exact same height floor(log2(n)).
 * - Phase 1 (buildMinHeap): Still calls heapifyDown on n/2 non-leaf nodes,
 *   summing to O(n) total comparisons/swaps.
 * - Phase 2 (Extraction loop): Still performs n - 1 swaps of the root (now the
 *   minimum element) to the end of the unsorted prefix, followed by an O(log n)
 *   sift-down on the reduced heap. Total cost: O(n) + (n - 1)*O(log n) = O(n log n).
 */
public class Exercise2 {

    // Sifts down to maintain the MIN-HEAP property (parent <= children)
    static void heapifyDownMin(int[] arr, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // CHANGED LINE 1: '<' instead of '>' to select the smaller child
        if (left < n && arr[left] < arr[smallest]) smallest = left;
        // CHANGED LINE 2: '<' instead of '>' to select the smaller child
        if (right < n && arr[right] < arr[smallest]) smallest = right;

        if (smallest != i) {
            int temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;
            heapifyDownMin(arr, n, smallest);
        }
    }

    static void buildMinHeap(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDownMin(arr, n, i);
        }
    }

    static void heapSortDescending(int[] arr) {
        int n = arr.length;
        buildMinHeap(arr);

        // Repeatedly swap the current minimum (arr[0]) to the end of the active heap,
        // so the smallest elements accumulate at the right end -> descending order!
        for (int end = n - 1; end > 0; end--) {
            int temp = arr[0];
            arr[0] = arr[end];
            arr[end] = temp;
            heapifyDownMin(arr, end, 0);
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 10, 3, 5, 1, 8, 9, 2, 7};

        System.out.println("Before:                " + Arrays.toString(arr));

        heapSortDescending(arr);

        System.out.println("After (Descending):    " + Arrays.toString(arr));

        System.out.println("\n=== Summary of Changes & Complexity ===");
        System.out.println("1. Lines Changed: Only the two child-comparison conditions in heapifyDown()");
        System.out.println("   change from '>' (max-heap) to '<' (min-heap).");
        System.out.println("2. Why O(n log n): Building the min-heap takes O(n) time, and each of the");
        System.out.println("   n - 1 minimum extractions sifts down at most the tree height O(log n).");
    }
}
