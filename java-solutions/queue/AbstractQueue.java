package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        assert size > 0;
        return dequeueImpl();
    }

    protected abstract Object dequeueImpl();


    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public int count(Object element) {
        assert element != null;
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (element.equals(element())) {
                result++;
            }
            enqueue(dequeue());
        }
        return result;
    }
}
