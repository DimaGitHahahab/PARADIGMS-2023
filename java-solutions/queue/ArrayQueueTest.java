package queue;

public class ArrayQueueTest {
    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        for (int i = 0; i < 5; i++) {
            queue1.enqueue(i);
            queue2.enqueue(5 - i);
        }
        while (!queue1.isEmpty()) {
            System.out.println("q1: " + queue1.dequeue());
        }
        while (!queue2.isEmpty()) {
            System.out.println("q2: " + queue2.dequeue());
        }
    }
}
