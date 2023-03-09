package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        ArrayQueueModule a = new ArrayQueueModule();
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.dequeue());
        }
    }
}
