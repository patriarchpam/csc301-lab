import java.util.Arrays;

/**
 * Problem 1: Build a Max-Heap From an Unsorted Array.
 * 
 * Objective:
 * Rearrange an arbitrary array in place so that it satisfies the max-heap
 * property: every parent is greater than or equal to both of its children.
 * 
 * Complexity:
 * - Time: O(n) for bottom-up heap construction.
 * - Space: O(log n) for the recursion stack inside heapifyDown.
 */
public class Problem1 {

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
            heapifyDown(arr, n, largest);   // continue sifting down
        }
    }

    static void buildMaxHeap(int[] arr) {
        int n = arr.length;
        // start from the last non-leaf node, work backward to the root
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i);
        }
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
        int[] arr = {4, 10, 3, 5, 1, 8, 9, 2, 7};

        System.out.println("Before: " + Arrays.toString(arr));

        buildMaxHeap(arr);

        System.out.println("After:  " + Arrays.toString(arr));
        System.out.println("Valid max-heap? " + isMaxHeap(arr));
    }
}
