import java.util.Arrays;

/**
 * Exercise 1: Bottom-Up buildMaxHeap() vs. Top-Down insertOneAtATime() (Sift-Up).
 * 
 * Objective:
 * Implement a second version, insertOneAtATime(), that builds the heap by
 * calling a siftUp-based insert() method n times on an initially empty array.
 * Compare the final heap SHAPE produced against buildMaxHeap() on the same
 * input {4, 10, 3, 5, 1, 8, 9, 2, 7}, and state whether the two methods
 * necessarily produce an identical array.
 * 
 * Comparison & Analysis:
 * - Bottom-up buildMaxHeap() produces:    [10, 7, 9, 5, 1, 8, 3, 2, 4] (O(n) time)
 * - Top-down insertOneAtATime() produces: [10, 7, 9, 5, 1, 3, 8, 2, 4] (O(n log n) time)
 * - Do they necessarily produce an identical array? NO!
 *   Look at indices 5 and 6 (the children of node 9 at index 2):
 *   - In buildMaxHeap(), index 2 (9) has left child 8 and right child 3.
 *   - In insertOneAtATime(), index 2 (9) has left child 3 and right child 8.
 *   Both arrays are 100% valid max-heaps because the heap-order property only
 *   requires parent >= children (9 >= 8 and 9 >= 3); it places NO ordering
 *   constraint between left and right siblings. Different construction histories
 *   (sifting existing subtrees down vs. sifting new leaves up) can therefore
 *   yield different valid max-heap configurations for the same input sequence.
 */
public class Exercise1 {

    // --- Method 1: Bottom-Up Heapify (O(n)) ---
    static void heapifyDown(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) largest = left;
        if (right < n && arr[right] > arr[largest]) largest = right;

        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapifyDown(arr, n, largest);
        }
    }

    static void buildMaxHeap(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
    }

    // --- Method 2: Top-Down Insert One-at-a-Time Using Sift-Up (O(n log n)) ---
    static void siftUp(int[] heap, int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] > heap[parent]) {
                int temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                index = parent; // continue sifting up toward the root
            } else {
                break; // heap property satisfied
            }
        }
    }

    static void insert(int[] heap, int currentSize, int value) {
        heap[currentSize] = value; // place new element at the next available leaf slot
        siftUp(heap, currentSize); // restore max-heap property by sifting up
    }

    static int[] insertOneAtATime(int[] input) {
        int[] heap = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            insert(heap, i, input[i]);
        }
        return heap;
    }

    static boolean isMaxHeap(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int left = 2 * i + 1, right = 2 * i + 2;
            if (left < n && arr[left] > arr[i]) return false;
            if (right < n && arr[right] > arr[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] original = {4, 10, 3, 5, 1, 8, 9, 2, 7};
        System.out.println("Original Input:                 " + Arrays.toString(original));

        // 1. Bottom-up buildMaxHeap()
        int[] bottomUpHeap = Arrays.copyOf(original, original.length);
        buildMaxHeap(bottomUpHeap);
        System.out.println("\n1. buildMaxHeap() [Bottom-Up]:  " + Arrays.toString(bottomUpHeap));
        System.out.println("   Valid max-heap?              " + isMaxHeap(bottomUpHeap));

        // 2. Top-down insertOneAtATime()
        int[] topDownHeap = insertOneAtATime(original);
        System.out.println("\n2. insertOneAtATime() [Sift-Up]:" + Arrays.toString(topDownHeap));
        System.out.println("   Valid max-heap?              " + isMaxHeap(topDownHeap));

        // 3. Compare shapes
        boolean identical = Arrays.equals(bottomUpHeap, topDownHeap);
        System.out.println("\nAre the two arrays identical?   " + identical);
        System.out.println("\n=== Shape & Theoretical Comparison ===");
        System.out.println("- Notice indices 5 and 6 (children of node 9 at index 2):");
        System.out.println("  * buildMaxHeap() places 8 at index 5 (left child of 9) and 3 at index 6 (right child of 9).");
        System.out.println("  * insertOneAtATime() places 3 at index 5 (left child of 9) and 8 at index 6 (right child of 9).");
        System.out.println("- Why both are valid: The max-heap invariant only requires parent >= children;");
        System.out.println("  it does NOT specify an order between left and right siblings.");
        System.out.println("- Therefore, the two methods do NOT necessarily produce an identical array.");
    }
}
