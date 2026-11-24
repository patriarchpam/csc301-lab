import java.util.Arrays;

/**
 * Problem 2: Implement Heap Sort Using a Max-Heap.
 * 
 * Objective:
 * Sort an array in ascending order by repeatedly extracting the maximum element
 * from a max-heap and placing it at the end of the unsorted region, entirely
 * in place with no auxiliary array.
 * 
 * Complexity:
 * - Time: O(n log n) overall — O(n) initial build + (n - 1) O(log n) extractions.
 * - Space: O(1) auxiliary space (in-place sorting; O(log n) recursion stack).
 */
public class Problem2 {

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

    static void heapSort(int[] arr) {
        int n = arr.length;
        buildMaxHeap(arr);

        // repeatedly move the current max (root) to the end,
        // then shrink the heap and re-sift the new root down
        for (int end = n - 1; end > 0; end--) {
            int temp = arr[0];
            arr[0] = arr[end];
            arr[end] = temp;
            heapifyDown(arr, end, 0);   // note: heap size is now 'end', not n
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 10, 3, 5, 1, 8, 9, 2, 7};

        System.out.println("Before: " + Arrays.toString(arr));

        heapSort(arr);

        System.out.println("After:  " + Arrays.toString(arr));
    }
}
