/**
 * Problem 5: Design and Implement Your Own Hash Map With Collision Handling.
 * 
 * Objective:
 * Implement a hash map from first principles — including hash function mapping,
 * a bucket array, and separate chaining collision resolution — supporting
 * put, get, remove, and size operations.
 * 
 * Theory / Mechanism:
 * - A hash map uses an array of buckets.
 * - Hash function: getBucketIndex(key) = Math.abs(key.hashCode()) % numBuckets.
 * - Collision: When two different keys hash to the same bucket index (e.g. "fig" and "apple"
 *   both hash to bucket 0 in a 5-bucket array).
 * - Separate Chaining: Each bucket holds the head of a singly linked list of entries.
 *   Colliding entries join the same chain.
 * - Duplicate Keys: put() must first scan the chain; if key exists, overwrite value.
 *   If not found, insert a new Entry node at the HEAD of the chain.
 * 
 * Complexity:
 * - Time: O(1) average for put, get, remove (when chains are short).
 * - Time: O(n) worst-case if all keys hash to the same bucket (forming one long list).
 * - Space: O(n + b) where n is entries count and b is bucket array capacity.
 */
public class Problem5 {

    // Node class representing an individual key-value pair in a separate-chaining linked list
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

    // Constructor to initialize hash table with a specified number of buckets
    public Problem5(int numBuckets) {
        this.numBuckets = numBuckets;
        this.buckets = new Entry[numBuckets];
        this.size = 0;
    }

    // Maps any string key to a valid bucket array index [0, numBuckets - 1]
    private int getBucketIndex(String key) {
        // Math.abs handles potential negative integer overflow from hashCode()
        return Math.abs(key.hashCode()) % numBuckets;
    }

    // Inserts or updates a key-value pair
    public void put(String key, int value) {
        int index = getBucketIndex(key);
        Entry head = buckets[index];

        // Step 1: Scan the linked chain to check if the key already exists
        Entry current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                // Update existing value and return
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Step 2: Key was not found; insert a new Entry at the HEAD of the chain
        Entry newEntry = new Entry(key, value);
        newEntry.next = head;
        buckets[index] = newEntry;
        size++;
    }

    // Retrieves value associated with key, or returns null if not found
    public Integer get(String key) {
        int index = getBucketIndex(key);
        Entry current = buckets[index];

        // Walk the chain searching for matching key
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        // Key not found in this bucket chain
        return null;
    }

    // Removes a key-value mapping from the hash map; returns true if removed
    public boolean remove(String key) {
        int index = getBucketIndex(key);
        Entry current = buckets[index];
        Entry prev = null;

        // Traverse the chain tracking the previous node to unlink cleanly
        while (current != null) {
            if (current.key.equals(key)) {
                // Unlink current node
                if (prev == null) {
                    // Removing head of the chain
                    buckets[index] = current.next;
                } else {
                    // Removing internal or tail node of the chain
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        // Key did not exist in the map
        return false;
    }

    // Returns total count of active key-value pairs stored in the map
    public int size() {
        return size;
    }

    // Prints the internal structure of all buckets and their chains
    public void printTable() {
        for (int i = 0; i < numBuckets; i++) {
            System.out.print("bucket " + i + ": ");
            Entry current = buckets[i];
            if (current == null) {
                System.out.print("null");
            }
            while (current != null) {
                System.out.print(current.key + ":" + current.value);
                if (current.next != null) {
                    System.out.print(" -> ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    // Main method to demonstrate hash table operations
    public static void main(String[] args) {
        System.out.println("=== Testing CustomHashMap (Problem 5) ===");

        Problem5 map = new Problem5(5);

        // Insert key-value pairs
        map.put("apple", 3);
        map.put("pear", 7);
        map.put("plum", 2);
        map.put("fig", 5);

        // Display internal bucket chains
        map.printTable();

        // Retrieve existing and non-existing keys
        System.out.println("get(\"plum\") = " + map.get("plum"));
        System.out.println("get(\"mango\") = " + map.get("mango"));

        // Update existing key "apple"
        map.put("apple", 10);
        System.out.println("after update, apple = " + map.get("apple"));

        // Remove key "pear"
        System.out.println("remove(\"pear\") = " + map.remove("pear"));
        System.out.println("size = " + map.size());
    }
}
