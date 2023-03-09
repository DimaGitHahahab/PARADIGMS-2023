package queue;

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();
        for (int i = 0; i < 5; i++) {
            ArrayQueueADT.enqueue(queue1, i);
            ArrayQueueADT.enqueue(queue2, 5 - i);
        }
        while (!ArrayQueueADT.isEmpty(queue1)) {
            System.out.println("q1: " + ArrayQueueADT.dequeue(queue1));
        }
        while (!ArrayQueueADT.isEmpty(queue2)) {
            System.out.println("q2: " + ArrayQueueADT.dequeue(queue2));
        }
    }
}
