package queue;

// Let n = size of queue
// Let immutable(n): for i=1..n: a'[i] == a[i]
public interface Queue {
    // Pred: element != null
    // Post: n = n' + 1 && a[n'] = element && immutable(n)
    void enqueue(Object element);

    // Pred: n >= 1
    // Post: R = a[0] && immutable(n)
    Object element();

    // Pred: n >= 1
    // Post: R = a[0] && n = n' - 1
    Object dequeue();

    // Pred: true
    // Post: R = n && immutable(n)
    int size();

    // Pred: true
    // Post: R = (n = 0) && immutable(n)
    boolean isEmpty();

    // Pred: true
    // Post: n' = 0
    void clear();


    // Pred: element != null
    // Post: R = amount of (element)s && immutable(n)
    int count(Object element);

}
