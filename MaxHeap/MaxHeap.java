import java.util.ArrayList;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Jadon Grossberg
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("There either contains a null element or the array is null");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size / 2; i > 0; i--) {
            int index = i;
            while (true) {
                index = downHeap(index);
                if (index == -1) {
                    break;
                }
            }
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (backingArray.length == size + 1) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < (backingArray.length); i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[size + 1] = data;
        size++;
        int index = size;
        while (true) {
            if (!upHeap(index)) {
                break;
            }
            index = index / 2;
        }
    }

    /**
     * helper function for the add function
     *
     * If the parent is less than the index passed to it, it swaps them
     *
     * @param index index to be upHeaped in the
     * @return true if the swap was done, false if there wasn't; used to check if another must be done.
     */
    private boolean upHeap(int index) {
        if (index != 1 && backingArray[index].compareTo(backingArray[index / 2]) > 0) {
            T temp = backingArray[index / 2];
            backingArray[index / 2] = backingArray[index];
            backingArray[index] = temp;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        int index = 1;
        size--;
        while (true) {
            index = downHeap(index);
            if (index == -1) {
                break;
            }
        }
        return removed;
    }

    /**
     * Helper method used to check if a node should be switched with either of it's children
     *
     * Checks to see if the array is long enough to even have the children nodes too
     *
     * @param index index to be downheaped
     * @return returns the index of swap destination; if there is no swap, returns a -1
     */
    private int downHeap(int index) {
        int newIndex = -1;
        if ((index * 2 < backingArray.length && backingArray[index * 2] != null)
                || (index * 2 + 1 < backingArray.length && backingArray[index * 2 + 1] != null)) {
            if ((index * 2 + 1 >= backingArray.length || backingArray[index * 2 + 1] == null)
                    || backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) > 0) {
                if (backingArray[index].compareTo(backingArray[index * 2]) < 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                    newIndex = index * 2;
                }
            } else if (backingArray[index].compareTo(backingArray[index * 2 + 1]) < 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[index * 2 + 1];
                backingArray[index * 2 + 1] = temp;
                newIndex = index * 2 + 1;
            }
        }
        return newIndex;

    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
