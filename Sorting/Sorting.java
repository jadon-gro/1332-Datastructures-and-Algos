import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jadon Grosserg
 * @version 1.0
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j != 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }

        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        boolean swapsMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int tempEndInd = endInd;
            for (int i  = startInd; i < tempEndInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    endInd = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                int tempStartInd = startInd;
                for (int i = endInd; i > tempStartInd; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        swapsMade = true;
                        startInd = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null");
        }
        if (arr.length == 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[length - midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        for (int i = midIndex; i < length; i++) {
            right[i - midIndex] = arr[i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            int current = 0;
            int num = arr[i];
            while (num != 0) {
                num /= 10;
                ++current;
            }
            if (current > k) {
                k = current;
            }
        }
        int product = 1;
        for (int w = 0; w < k; w++) {
            for (int i = 0; i < arr.length; i++) {
                int lsd = (arr[i] / product) % 10;
                if (buckets[9 + lsd] == null) {
                    buckets[9 + lsd] = new LinkedList<>();
                }
                buckets[9 + lsd].add(arr[i]);
            }
            int i = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (bucket != null && !bucket.isEmpty()) {
                    arr[i++] = bucket.removeFirst();
                }
            }
            product = product * 10;
        }

    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array or comparator or rand is null");
        }
        quickHelper(0, arr.length - 1, arr, comparator, rand);
    }
    /**
     * Helper method for quicksort
     * @param start starting index
     * @param end ending index
     * @param arr array to be sorted
     * @param comparator Comparator object to be used for comparisons
     * @param rand Random object for generating random pivot posiitons
     * @param <T> tbh idk why this goes here, i think it's to tell the compiler that it uses a generic type?
     */
    private static <T> void quickHelper(int start, int end, T[] arr, Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }
        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        T temp = arr[pivotIdx];
        arr[pivotIdx] = arr[start];
        arr[start] = temp;
        int i = start + 1;
        int j = end;
        while (j >= i) {
            while (j >= i && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (j >= i && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (j >= i) {
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }
        T temp3 = arr[start];
        arr[start] = arr[j];
        arr[j] = temp3;
        quickHelper(start, j - 1, arr, comparator, rand);
        quickHelper(j + 1, end, arr, comparator, rand);
    }
}
