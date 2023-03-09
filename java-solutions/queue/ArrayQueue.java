package queue;

import java.util.Arrays;

public class ArrayQueue {
    private Object[] elements = new Object[2];
    private int size = 0;
    private int head = 0;

    // Pred: element != null
    // Post: n = n' + 1 && a[n'] = element && immutable(n)
    public void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        if (head + size >= elements.length) {
            elements[head + size - elements.length] = element;
        } else {
            elements[head + size] = element;
        }
        size++;
    }

    // Pred: true
    // Post: immutable(n)
    private void ensureCapacity(int size) {
        if (elements.length < size) {
            if (elements.length - head < size) {
                Object[] tempBuffer = new Object[elements.length - head];
                System.arraycopy(elements, head, tempBuffer, 0, tempBuffer.length);
                System.arraycopy(elements, 0, elements, elements.length - head, head);
                System.arraycopy(tempBuffer, 0, elements, 0, tempBuffer.length);
                head = 0;
            }
            elements = Arrays.copyOf(elements, 2 * size);
        }

    }

    // Pred: n >= 1
    // Post: R = a[0] && immutable(n)
    public Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pred: n >= 1
    // Post: R = a[0] && n = n' - 1
    public Object dequeue() {
        assert size > 0;
        Object result = elements[head];
        elements[head] = null;
        if (head == elements.length - 1) {
            head = 0;
        } else {
            head++;
        }
        size--;
        return result;
    }

    // Pred: true
    // Post: R = n && immutable(n)
    public int size() {
        return size;
    }

    // Pred: true
    // Post: R = (n = 0) && immutable(n)
    public boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: n' = 0
    public void clear() {
        elements = new Object[2];
        size = 0;
        head = 0;
    }

    // Pred: true
    // Post: R = "[a_0, a_1, ... , a_(n - 1)]" && immutable(n)
    public String toStr() {
        StringBuilder result = new StringBuilder();
        int idx;
        result.append("[");
        for (int i = 0; i < size(); i++) {
            if (head + i < elements.length) {
                idx = head + i;
            } else {
                idx = head + i - elements.length;
            }
            result.append(elements[idx]);
            if (i != size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
