package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Supplier;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(() -> new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> entryValue = new ArrayStack<>();

            for(Item<A,HashTrieNode> value : this.pointers) {
                entryValue.add(new SimpleEntry(value.key, value.value));
            }
            return entryValue.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        HashTrieNode root = (HashTrieNode) this.root;
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if (key.isEmpty()) {
            V temp = root.value;
            root.value = value;
            size++;
            return temp;
        }
        for (A letter : key) {
            if (root.pointers.find(letter) == null) {
                root.pointers.insert(letter, new HashTrieNode());
            }
            root = root.pointers.find(letter);
        }
        V temp = null;
        //duplicate key
        if (root.value != null) {
            temp = root.value;
            size--;
        }
        root.value = value;
        size++;
        return temp;
    }

    @Override
    public V find(K key) {
        HashTrieNode root = (HashTrieNode) this.root;
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (key.isEmpty()) {
            return root.value;
        }
        for (A letter : key) {
            if (root.pointers.find(letter) == null) {
                return null;
            }
            root = root.pointers.find(letter);
        }
        return root.value;

    }

    @Override
    public boolean findPrefix(K key) {
        HashTrieNode root = (HashTrieNode) this.root;
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (root.pointers.isEmpty() && root.value == null) {
            return false;
        }
        if (root.value != null || find(key) != null) {
            return true;
        }
        for (A letter : key) {
            if (root.pointers.find(letter) == null) {
                return false;
            }
            root = root.pointers.find(letter);
        }
        //if pointers point to any other A
        return !root.pointers.isEmpty();
    }

    @Override
    public void delete(K key) {
        HashTrieNode root = (HashTrieNode) this.root;
        if (find(key) == null) {
            return;
        }
        if (key.isEmpty()) {
            root.value = null;
            size--;
            return;
        }
//        deleteHelper(root, root, key.iterator().next(), key.iterator());
        HashTrieNode tempNode = root;
        A tempLetter = key.iterator().next();
        for (A letter : key) {
            if (root.pointers.size() > 1 || root.value != null) {
                tempLetter = letter;
                tempNode = root;
            }
            root = root.pointers.find(letter);
        }
        //lazy deletion if key is a prefix as to not delete words such as doggy when deleting dog
        if (!root.pointers.isEmpty()) {
            root.value = null;
            size--;
        }
        //deletes entire branch from latest split in trie
        else {
            size--;
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
