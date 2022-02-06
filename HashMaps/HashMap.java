import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Your implementation of a HashMap.
 *
 * @author Jadon Grossberg
 * @version 1.0
 */
public class HashMap<K, V> {

    /*
     * The initial capacity of the HashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the HashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new HashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is okay
     * if the load factor is equal to max LF). For example, let's say the
     * array is of length 5 and the current size is 3 (LF = 0.6). For this
     * example, assume that no elements are removed in between steps. If
     * another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Either value or key passed to put is null");
        }
        V output = null;
        if (((double) (size + 1)) / ((double) table.length) >= MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] copy = table;
            table = (MapEntry<K, V>[]) new MapEntry[(copy.length * 2) + 1];
            int oldSize = size;
            size = 0;
            MapEntry<K, V> current = copy[0];
            int counter = 0;
            int index = 0;
            while (counter < oldSize) {
                if (current == null) {
                    index++;
                    current = copy[index];
                } else {
                    put(current.getKey(), current.getValue());
                    counter++;
                    if (current.getNext() == null) {
                        index++;
                        current = copy[index];
                    } else {
                        current = current.getNext();
                    }
                }
            }
        }
        int computedInx = Math.abs(key.hashCode() % table.length);
        MapEntry<K, V> curr = table[computedInx];
        boolean duplicate = false;
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                output = curr.getValue();
                curr.setValue(value);
                duplicate = true;
                break;
            }
            curr = curr.getNext();
        }
        if (!duplicate) {
            MapEntry<K, V> inserted = new MapEntry<K, V>(key, value, table[computedInx]);
            table[computedInx] = inserted;
            size++;
        }
        return output;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key the key to remove
     * @return the value associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("the key you are trying to remove is null!");
        }
        boolean found = false;
        V removed = null;
        MapEntry<K, V> current = table[0];
        MapEntry<K, V> last = table[0];
        int counter = 0;
        int index = 0;
        while (counter < size) {
            if (current == null) {
                index++;
                current = table[index];
            } else {
                if (current.getKey() == key && current.equals(table[index])) {
                    removed = current.getValue();
                    table[index] = current.getNext();
                    found = true;
                    break;
                } else if (current.getKey() == key) {
                    removed = current.getValue();
                    last.setNext(current.getNext());
                    found = true;
                    break;
                }
                last = current;
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = table[index];
                } else {
                    current = current.getNext();
                }
            }
        }
        if (!found) {
            throw new NoSuchElementException("The key to be removed was not found!");
        }
        size--;
        return removed;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key to be fetched is null!");
        }
        MapEntry<K, V> current = table[0];
        int counter = 0;
        int index = 0;
        V target = null;
        while (counter < size) {
            if (current == null) {
                index++;
                current = table[index];
            } else {
                if (current.getKey().equals(key)) {
                    target = current.getValue();
                    break;
                }
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = table[index];
                } else {
                    current = current.getNext();
                }
            }
        }
        if (target == null) {
            throw new NoSuchElementException("The key to be fetched was not found!");
        }
        return target;
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key passed into the \"containsKey\" method is null!");
        }
        boolean found = false;
        MapEntry<K, V> current = table[0];
        int counter = 0;
        int index = 0;
        while (counter < size) {
            if (current == null) {
                index++;
                current = table[index];
            } else {
                if (current.getKey().equals(key)) {
                    found = true;
                    break;
                }
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = table[index];
                } else {
                    current = current.getNext();
                }
            }
        }
        return found;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>();
        MapEntry<K, V> current = table[0];
        int counter = 0;
        int index = 0;
        while (counter < size) {
            if (current == null) {
                index++;
                current = table[index];
            } else {
                set.add(current.getKey());
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = table[index];
                } else {
                    current = current.getNext();
                }
            }
        }
        return set;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> list = new ArrayList<V>();
        MapEntry<K, V> current = table[0];
        int counter = 0;
        int index = 0;
        while (counter < size) {
            if (current == null) {
                index++;
                current = table[index];
            } else {
                list.add(current.getValue());
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = table[index];
                } else {
                    current = current.getNext();
                }
            }
        }
        return list;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("new length is smaller than size!");
        }
        MapEntry<K, V>[] copy = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        MapEntry<K, V> current = copy[0];
        int counter = 0;
        int index = 0;
        while (counter < size) {
            if (current == null) {
                index++;
                current = copy[index];
            } else {
                int computedInx = Math.abs(current.getKey().hashCode() % table.length);
                if (copy[index] != null) {
                    current.setNext(table[index]);
                }
                table[computedInx] = current;
                counter++;
                if (current.getNext() == null) {
                    index++;
                    current = copy[index];
                } else {
                    current = current.getNext();
                }
            }
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     */
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
