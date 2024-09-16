package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import datastructures.worklists.CircularArrayFIFOQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private int capacity;
    private int capacityIndex;
    private Dictionary<K, V>[] table;
    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        capacityIndex = 0;
        capacity = PRIME_SIZES[capacityIndex];
        this.size = 0;
        this.table = (Dictionary<K, V>[]) new Dictionary[capacity];

    }
    @Override
    public V insert(K key, V value) {
        V oldValue = find(key);
        int index = key.hashCode() % capacity;
        if (index < 0) {
            index *= -1;
        }
        if (table[index] == null) {
            table[index] = newChain.get();

        }
        table[index].insert(key, value);
        if (oldValue == null) {
            this.size++;
        }
        double loadFactor = this.size;
        loadFactor /= capacity;
        if (loadFactor >= 0.75) {
            rehash();
        }
        return oldValue;
    }
    @Override
    public V find(K key) {
        int index = key.hashCode() % capacity;
        if (index < 0) {
            index *= -1;
        }
        if (table[index] == null) {
            return null;
        }
        return table[index].find(key);
    }
    private void rehash() {
        capacityIndex++;
        if (capacityIndex < PRIME_SIZES.length) {
            capacity = PRIME_SIZES[capacityIndex];
        }
        else {
            capacity = capacity * 2 + 1;
        }
        Dictionary<K, V>[] temp = new Dictionary[capacity];
        for (Item<K, V> item : this) {
            int index = item.key.hashCode() % capacity;
            if (index < 0) {
                index *= -1;
            }
            if (temp[index] == null) {
                temp[index] = newChain.get();
            }
            temp[index].insert(item.key, item.value);
        }
        table = temp;
    }
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new CHTIterator();
    }
    private class CHTIterator extends SimpleIterator<Item<K, V>> {
        private int counter;
        private int count;
        private Iterator<Item<K, V>> currentIterator;
        public CHTIterator() {
            counter = 0;
            count = 0;
            currentIterator = (table[0] == null) ? null : table[0].iterator();
        }
        @Override
        public boolean hasNext() {
            if (currentIterator == null || !currentIterator.hasNext()) {
                while (++counter < table.length) {
                    if (table[counter] != null) {
                        currentIterator = table[counter].iterator();
                        return count < size;
                    }
                }
            }
            return count < size;
        }
        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            count++;
            return currentIterator.next();
        }
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
