public class Exercise1 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node head;

    static void insertAtEnd(int... values) {
        for (int value : values) {
            Node newNode = new Node(value);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null)
                    temp = temp.next;
                temp.next = newNode;
            }
        }
    }

    static void traverse() {
        Node temp = head;
        int count = 0;
        System.out.print("List: ");
        while (temp != null) {
            System.out.print(temp.data);
            if (temp.next != null)
                System.out.print(" -> ");
            temp = temp.next;
            count++;
        }
        System.out.println(" -> NULL");
        System.out.println("Total nodes: " + count);
    }

    static void search(int target) {
        Node temp = head;
        int index = 0;
        while (temp != null) {
            if (temp.data == target) {
                System.out.println("Value " + target + " found at position: " + index);
                return;
            }
            temp = temp.next;
            index++;
        }
        System.out.println("Value " + target + " not found (position: -1)");
    }

    public static void main(String[] args) {
        insertAtEnd(10, 25, 40, 55, 20);

        traverse();
        search(40);
        search(100);
    }
}
