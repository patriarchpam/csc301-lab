class MaxMin {

    static int max, min;

    static void findMaxMin(int[] arr) {
        max = arr[0];
        min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 45, 3, 89, 27, 6, 49};
        findMaxMin(arr);
        System.out.println("Array:   " + java.util.Arrays.toString(arr));
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);
    }
}
