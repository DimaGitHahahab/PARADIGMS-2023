package queue;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    private static class Node {
        private final Object value;
        private Node next;

        private Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
    @Override
    protected void enqueueImpl(Object element) {
        Node newNode = new Node(element, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }
    @Override
    protected Object elementImpl() {
        return head.value;
    }
    @Override
    protected Object dequeueImpl() {
        Object result = head.value;
        head = head.next;
        size--;
        return result;
    }
    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

}
