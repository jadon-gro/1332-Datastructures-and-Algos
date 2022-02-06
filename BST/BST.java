import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Jadon Grossberg
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        root = null;
        size = 0;
        for (T piece : data) {
            this.add(piece);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        root = rAdd(root, data);
    }

    /**
     * recuresive helper method for adding
     *
     * @param curr current node for recursion
     * @param data data to be added
     * @return returns current node for recursion
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("The data is not in the BST");
        }
        size--;
        return dummy.getData();
    }

    /**
     * recursive helper method for removal
     *
     * @param curr current node to be passed and changed for recursion
     * @param data data to be removed
     * @param dummy dummy node to store the removed data
     * @return returns the node for pointer reinforcement
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            if (curr.getRight() == null && curr.getLeft() == null) {
                dummy.setData(curr.getData());
                return null;
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                dummy.setData(curr.getData());
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                dummy.setData(curr.getData());
                return curr.getLeft();
            } else {
                BSTNode<T> dummy1 = new BSTNode<T>(null);
                dummy.setData(curr.getData());
                curr.setRight(findSucc(curr.getRight(), dummy1));
                curr.setData(dummy1.getData());
            }
        }
        return curr;
    }

    /**
     * recursive helper method for removal
     *
     * finds the successor node to be removed and replace a node hidden under children nodes
     *
     * @param curr current node in the recursive model
     * @param removed storage node for removed node
     * @return return current node for pointer reinforcement
     */
    private BSTNode<T> findSucc(BSTNode<T> curr, BSTNode<T> removed) {
        if (curr.getLeft() == null) {
            removed.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(findSucc(curr.getLeft(), removed));
        }
        return curr;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rFind(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("The data is not in the BST!");
        }
        return dummy.getData();
    }

    /**
     * recursive helper method for the find method
     *
     * goes down each side of the tree depending on whether curr is less or greater than the target data
     *
     * @param curr current node for recursion
     * @param data data being searched for
     * @param dummy dummy node for storage
     * @return returns the current node for pointer reinforcement
     */
    private BSTNode<T> rFind(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            return null;
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rFind(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rFind(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
        }
        return curr;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you passed in is null!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rFind(root, data, dummy);
        return dummy.getData() != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        root = rPreorder(list, root);
        return list;
    }

    /**
     * recuresive preorder helper method
     *
     * @param list list to be built in preorder
     * @param curr current node for recursion
     * @return current node for pointer reinforcement
     */
    private BSTNode<T> rPreorder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            list.add(curr.getData());
            curr.setLeft(rPreorder(list, curr.getLeft()));
            curr.setRight(rPreorder(list, curr.getRight()));
        }
        return curr;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        root = rInorder(list, root);
        return list;
    }

    /**
     * Recursive helper method to put the backing array into inorder
     *
     * @param list list to build in order
     * @param curr current node for recursion
     * @return returns the current node for recursion
     */
    private BSTNode<T> rInorder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            curr.setLeft(rInorder(list, curr.getLeft()));
            list.add(curr.getData());
            curr.setRight(rInorder(list, curr.getRight()));
        }
        return curr;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        root = rPostorder(list, root);
        return list;
    }

    /**
     * post order recursive helper method
     *
     * iterates through the tree in post order
     *
     * @param list list to be build
     * @param curr current node for recursion
     * @return returns the current node for recursion
     */
    private BSTNode<T> rPostorder(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            curr.setLeft(rPostorder(list, curr.getLeft()));
            curr.setRight(rPostorder(list, curr.getRight()));
            list.add(curr.getData());
        }
        return curr;
    }


    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<BSTNode<T>> list = new ArrayList<>();
        List<T> list2 = new ArrayList<>();
        if (root == null) {
            return list2;
        }
        list.add(root);
        while (!(list.isEmpty())) {
            BSTNode<T> curr = list.remove(0);
            if (curr != null) {
                list2.add(curr.getData());
                list.add(curr.getLeft());
                list.add(curr.getRight());
            }
        }
        return list2;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        BSTNode<Integer> height = new BSTNode<>(-1);
        root = rHeight(root, height);
        return height.getData();
    }

    /**
     * recuresive helper method for height
     * @param curr current method for recursion and pointer reinforcement
     * @param height height of node that gets added as you move up the stack
     * @return a dummy node cointaining the height of the subtree
     */
    private BSTNode<T> rHeight(BSTNode<T> curr, BSTNode<Integer> height) {
        if (curr != null) {
            curr.setLeft(rHeight(curr.getLeft(), height));
            int leftHeight = height.getData();

            curr.setRight(rHeight(curr.getRight(), height));
            int rightHeight = height.getData();

            height.setData(Math.max(leftHeight, rightHeight) + 1);
        } else {
            height.setData(-1);
        }
        return curr;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
