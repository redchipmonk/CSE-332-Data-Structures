package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.worklists.ArrayStack;
import java.lang.reflect.Array;
import java.security.Key;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    // TODO: Implement me!
    public AVLTree() {
        super();
    }
    public class AVLNode extends BSTNode {
        public int height;
        public AVLNode(K key, V value, int height) {
            super(key, value);
            this.height = height;
        }
    }
    protected BSTNode find(K key, V value) {
        return super.find(key, value);
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldValue = find(key);
        this.root = insertHelper((AVLNode) this.root, key, value);
        return oldValue;
    }
    protected AVLNode insertHelper(AVLNode root, K key, V value) {
        if (root == null) {
            this.size++;
            return new AVLNode(key, value, 1);
        }
        if (key.compareTo(root.key) < 0) {
            root.children[0] = insertHelper((AVLNode) root.children[0], key, value);
        }
        else if (key.compareTo(root.key) > 0) {
            root.children[1] = insertHelper((AVLNode) root.children[1], key, value);
        }
        else {
            root.value = value;
        }
        recalculateHeight(root);
        AVLNode temp = checkForUnbalances(root);
        if (temp == null) {
            return root;
        }
        return temp;
    }
    private AVLNode rotateLeft(AVLNode root) {
        AVLNode right = (AVLNode) root.children[1];
        AVLNode temp = root;
        AVLNode rightSubtree = (AVLNode) right.children[0];
        right.children[0] = temp;
        temp.children[1] = rightSubtree;
        root = right;
        recalculateHeight((AVLNode) root.children[0]);
        recalculateHeight(root);
        return root;
    }

    private AVLNode rotateRight(AVLNode root) {
        AVLNode left = (AVLNode) root.children[0];
        AVLNode temps = root;
        AVLNode temp = (AVLNode) left.children[1];
        left.children[1] = temps;
        root.children[0] = temp;
        root = left;
        recalculateHeight((AVLNode) root.children[1]);
        recalculateHeight(root);
        return root;
    }

    private AVLNode rotateRightLeft(AVLNode root) {
        root.children[1] = rotateRight((AVLNode) root.children[1]);
        root = rotateLeft(root);
        return root;
    }

    private AVLNode rotateLeftRight(AVLNode root) {
        root.children[0] = rotateLeft((AVLNode) root.children[0]);
        root = rotateRight(root);
        return root;
    }
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }
    private void recalculateHeight(AVLNode root) {
        int[] arr = calculateHeight(root);
        root.height = max(arr[0], arr[1]) + 1;
    }
    private int[] calculateHeight(AVLNode root) {
        int leftHeight = 0;
        int rightHeight = 0;
        if (root.children[0] != null) {
            leftHeight = ((AVLNode) root.children[0]).height;
        }
        if (root.children[1] != null) {
            rightHeight = ((AVLNode) root.children[1]).height;
        }
        return new int[]{leftHeight, rightHeight};
    }
    private AVLNode checkForUnbalances(AVLNode root) {
        int[] arr = calculateHeight(root);
        int balance = arr[1] - arr[0];
        if (balance > 1) {
            int[] rightHeights = calculateHeight((AVLNode) root.children[1]);
            if (rightHeights[1] > rightHeights[0]) {
                return rotateLeft(root);
            } else {
                return rotateRightLeft(root);
            }
        }
        else if (balance < -1) {
            int[] leftHeights = calculateHeight((AVLNode) root.children[0]);
            if (leftHeights[0] > leftHeights[1]) {
                return rotateRight(root);
            }
            else {
                return rotateLeftRight(root);
            }
        }
        return null;
    }
}
