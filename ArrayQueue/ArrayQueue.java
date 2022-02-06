import java.util.NoSuchElementException;
/**
 * Your implementation of various graph algorithms.
 *
 * @author Jadon Grossberg
 * @version 1.0
 */
public class Gr
public class ArrayQueue<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
    }

    /**
     * Adds the data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i backingArray.length; i++) {
                if (i + front >= size) {
                    temp[i] = backingArray[i + front - size];
                } else {
                    temp[i] = backingArray[i + front];
                }
            }
            temp[size] = data;
            backingArray = temp;
            front = 0;
        } else {
            backingArray[(front + size) % backingArray.length] = data;
        }
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Do not shrink the backing array.
     *
     * Replace any spots that you dequeue from with null.
     *
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        T removed = backingArray[front];
        backingArray[front] = null;
        if (front + 1 == size) {
            front = 0;
        } else {
            front = front + 1;
        }
        size--;
        if (size == 0) {
            front = 0;
        }
        return removed;
    }
    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }
        return backingArray[front];
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
