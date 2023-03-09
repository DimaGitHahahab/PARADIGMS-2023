package queue;

import java.util.Arrays;
// Model: a[1]..a[n]
// Invariant: for i=1..n: a[i] != null
// Let immutable(n): for i=1..n: a'[i] == a[i]
public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int size = 0;
    private int head = 0;
    // Pred: element != null
    // Post: n = n' + 1 && a[n'] = element && immutable(n)
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        ensureCapacity(queue, queue.size + 1);
        if (queue.head + queue.size >= queue.elements.length) {
            queue.elements[queue.head + queue.size - queue.elements.length] = element;
        } else {
            queue.elements[queue.head + queue.size] = element;
        }
        queue.size++;
    }
    // Pred: true
    // Post: immutable(n)
    private static void ensureCapacity(ArrayQueueADT queue, int size) {
        if (queue.elements.length < size) {
            if (queue.elements.length - queue.head < size) {
                Object[] tempBuffer = new Object[queue.elements.length - queue.head];
                System.arraycopy(queue.elements, queue.head, tempBuffer, 0, tempBuffer.length);
                System.arraycopy(queue.elements, 0, queue.elements, queue.elements.length - queue.head, queue.head);
                System.arraycopy(tempBuffer, 0, queue.elements, 0, tempBuffer.length);
                queue.head = 0;
            }
            queue.elements = Arrays.copyOf(queue.elements, 2 * size);
        }

    }
    // Pred: n >= 1
    // Post: R = a[0] && immutable(n)
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }// Pred: n >= 1
    // Post: R = a[0] && n = n' - 1
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object result = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        if (queue.head == queue.elements.length - 1) {
            queue.head = 0;
        } else {
            queue.head++;
        }
        queue.size--;
        return result;
    }
    // Pred: true
    // Post: R = n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    // Pred: true
    // Post: R = (n = 0) && immutable(n)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    // Pred: true
    // Post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        queue.size = 0;
        queue.head = 0;
        queue.elements = new Object[2];
    }
    // Pred: true
    // Post: R = "[a_0, a_1, ... , a_(n - 1)]" && immutable(n)
    public static String toStr(ArrayQueueADT queue) {
        StringBuilder result = new StringBuilder();
        int idx;
        result.append("[");
        for (int i = 0; i < queue.size; i++) {
            if (queue.head + i < queue.elements.length) {
                idx = queue.head + i;
            } else {
                idx = queue.head + i - queue.elements.length;
            }
            result.append(queue.elements[idx]);
            if (i != queue.size - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
