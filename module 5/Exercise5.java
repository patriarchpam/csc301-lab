/**
 * Exercise 5: Custom Hash Map with Dynamic Resizing and Rehashing.
 * 
 * Objective:
 * Implement a resize() method that doubles the bucket count whenever the
 * load factor (size / numBuckets) exceeds the 0.75 threshold, REHASHING every
 * existing entry into the enlarged bucket array.
 * 
 * Why Entries MUST Be Recomputed (Rehashed) Rather Than Simply Copied Across:
 * In a hash table, bucket placement depends on the formula:
 *     index = Math.abs(key.hashCode()) % numBuckets
 * Notice that 'numBuckets' is the modulo divisor!
 * When numBuckets doubles (e.g., from 5 to 10):
 * - Key "fig" has hashCode 101490.
 *   - Under 5 buckets:  101490 % 5  = bucket 0
 *   - Under 10 buckets: 101490 % 10 = bucket 0
 * - Key "apple" has hashCode 93029210.
 *   - Under 5 buckets:  93029210 % 5  = bucket 0 (COLLIDED with "fig"!)
 *   - Under 10 buckets: 93029210 % 10 = bucket 0
 * - Key "plum" has hashCode 3444085.
 *   - Under 5 buckets:  3444085 % 5  = bucket 0
 *   - Under 10 buckets: 3444085 % 10 = bucket 5! (MOVES to bucket 5!)
 * 
 * If we simply copied plum into bucket 0, a subsequent get("plum") on the 10-bucket
 * map would compute 3444085 % 10 = bucket 5, inspect bucket 5, find null, and falsely
 * report that "plum" does not exist!
 * Recomputing each index guarantees correct lookup under the new bucket count.
 * 
 * Amortised Time Complexity of put():
 * - A single resize operation touches all N entries and takes O(N) time.
 * - However, capacity doubles every time, so at least N/2 insertions must occur
 *   between consecutive resizes.
 * - Distributing the O(N) cost across N/2 insertions yields an AMORTISED cost of O(1) per put().
 */
public class Exercise5 {

    // Threshold above which resizing is triggered
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    // Node representing an entry in a bucket chain
    static class Entry {
        String key;
        int value;
        Entry next;

        Entry(String key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] buckets;
    private int numBuckets;
    private int size;

    // Constructor with initial bucket count
    public Exercise5(int initialBuckets) {
        this.numBuckets = initialBuckets;
        this.buckets = new Entry[numBuckets];
        this.size = 0;
    }

    // Hash function mapping a key to an index within [0, numBuckets - 1]
    private int getBucketIndex(String key, int bucketCount) {
        return Math.abs(key.hashCode()) % bucketCount;
    }

    public double currentLoadFactor() {
        return (double) size / numBuckets;
    }

    public int size() {
        return size;
    }

    public int getBucketCount() {
        return numBuckets;
    }

    // Doubles the bucket array and rehashes all existing entries into their new buckets
    private void resize() {
        int oldBucketsCount = numBuckets;
        int newBucketsCount = numBuckets * 2;
        Entry[] newBuckets = new Entry[newBucketsCount];

        System.out.println("\n==========================================================================");
        System.out.printf(">>> [RESIZE TRIGGERED] Size = %d, Buckets = %d, Load Factor = %.2f > %.2f%n",
                          size, oldBucketsCount, currentLoadFactor(), LOAD_FACTOR_THRESHOLD);
        System.out.println("    Doubling bucket count from " + oldBucketsCount + " -> " + newBucketsCount + " and rehashing all entries...");
        System.out.println("==========================================================================");

        // Traverse each bucket of the old array
        for (int i = 0; i < oldBucketsCount; i++) {
            Entry current = buckets[i];
            while (current != null) {
                // Save reference to the next node in the old chain
                Entry nextNode = current.next;

                // RECOMPUTE the bucket index using the NEW bucket count!
                int newIndex = getBucketIndex(current.key, newBucketsCount);

                // Insert current node at the HEAD of the new bucket chain
                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;

                System.out.printf("    Rehashed key '%s' (hash: %d) from old bucket [%d] -> new bucket [%d]%n",
                                  current.key, current.key.hashCode(), i, newIndex);

                // Advance to the saved next node
                current = nextNode;
            }
        }

        // Replace internal storage with resized structures
        this.buckets = newBuckets;
        this.numBuckets = newBucketsCount;

        System.out.printf(">>> Resizing Complete! New Load Factor: %.2f%n%n", currentLoadFactor());
    }

    // Inserts or updates a key-value pair, triggering resize if load factor exceeds threshold
    public void put(String key, int value) {
        int index = getBucketIndex(key, numBuckets);
        Entry head = buckets[index];

        // Check if key already exists; if so, update value in place
        Entry current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Insert new entry at the head of the current chain
        Entry newEntry = new Entry(key, value);
        newEntry.next = head;
        buckets[index] = newEntry;
        size++;

        System.out.printf("Put: '%s' -> %d | Bucket: [%d] | Size: %d/%d (Load Factor: %.2f)%n",
                          key, value, index, size, numBuckets, currentLoadFactor());

        // Check if load factor has exceeded the 0.75 threshold
        if (currentLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    // Retrieves value for a key, or null if not found
    public Integer get(String key) {
        int index = getBucketIndex(key, numBuckets);
        Entry current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    // Removes a key from the map
    public boolean remove(String key) {
        int index = getBucketIndex(key, numBuckets);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) buckets[index] = current.next;
                else prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    // Prints the internal structure of all buckets
    public void printTable() {
        System.out.println("--- Hash Table Layout (Buckets: " + numBuckets + ", Size: " + size + ") ---");
        for (int i = 0; i < numBuckets; i++) {
            System.out.print("  bucket " + i + ": ");
            Entry current = buckets[i];
            if (current == null) {
                System.out.print("null");
            }
            while (current != null) {
                System.out.print(current.key + ":" + current.value);
                if (current.next != null) System.out.print(" -> ");
                current = current.next;
            }
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("==========================================================================");
        System.out.println(" Exercise 5: Dynamic Hash Table Resizing & Rehashing Demonstration       ");
        System.out.println("==========================================================================");

        // Start with a small table of 4 buckets.
        // Resizing will trigger when size > 4 * 0.75 = 3 items (i.e. upon 4th insertion).
        Exercise5 map = new Exercise5(4);

        System.out.println("Initial capacity: 4 buckets | Load factor threshold: 0.75");
        System.out.println("Resizing will trigger on the 4th inserted element.\n");

        // Insert 3 items (Load factor reaches 3/4 = 0.75)
        map.put("apple", 10);
        map.put("pear", 20);
        map.put("plum", 30);
        map.printTable();

        // 4th insertion pushes load factor to 4/4 = 1.00 > 0.75 -> TRIGGERS RESIZE to 8 buckets!
        System.out.println("\nInserting 4th item ('fig') to exceed load factor threshold:");
        map.put("fig", 40);

        // Display updated table layout after resize
        map.printTable();

        // Insert more items to verify stability and lookup
        map.put("banana", 50);
        map.put("orange", 60);

        System.out.println("\nVerifying all keys remain retrievable after resizing:");
        String[] keys = {"apple", "pear", "plum", "fig", "banana", "orange", "grape"};
        for (String k : keys) {
            System.out.printf("  get(\"%-6s\") = %s%n", k, map.get(k));
        }

        System.out.println("\n=== Theoretical Answers & Analysis ===");
        System.out.println("1. Why entries MUST be recomputed:");
        System.out.println("   The bucket index formula is index = hash % numBuckets.");
        System.out.println("   Changing numBuckets alters the modulo divisor, redistributing keys across new buckets.");
        System.out.println("   Simply copying pointers would cause lookups to check the wrong bucket and fail.");
        System.out.println("2. Amortised Time Complexity of put():");
        System.out.println("   - Worst-case time during resize: O(N) to rehash all N elements.");
        System.out.println("   - Amortised time per put(): O(1) because capacity doubles, ensuring resizes are rare.");
    }
}
