package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    protected Node root;
    public class Node extends Item<K, V> {
        public Node next;
        public Node(K key, V value) {
            super(key, value);
            this.next = null;
        }
    }
    public MoveToFrontList() {
        super();
        this.root = null;
    }
    @Override
    public V insert(K key, V value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        V temp = find(key);
        if (root == null) {
            root = new Node(key, value);
            size++;
            return temp;
        }
        Node current = new Node(key, value);
        if (temp != null) {
            root = root.next;
            size--;
        }
        current.next = root;
        root = current;
        size++;
        return temp;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root.value;
        }
        Node current = root;
        Node previous = root;
        while (current != null) {
            if (current.key.equals(key)) {
                Node temp = current;
                previous.next = current.next;
                temp.next = root;
                root = temp;
                return temp.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFLIterator();
    }
    private class MTFLIterator extends SimpleIterator<Item<K, V>> {
        private Node current;
        public MTFLIterator() {
            if (MoveToFrontList.this.root != null) {
                this.current = MoveToFrontList.this.root;
            }
        }
        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item<K, V> next() {
            Item<K, V> value = new Item<K, V>(this.current);
            this.current = this.current.next;

            return value;
        }
    }
}
