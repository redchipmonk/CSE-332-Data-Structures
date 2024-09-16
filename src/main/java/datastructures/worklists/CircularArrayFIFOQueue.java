package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private int frontIndex;
    private int backIndex;
    private int size;
    private E[] array;
    private int capacity;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        frontIndex = 0;
        backIndex = -1;
        size = 0;
        this.capacity = capacity;
        array = (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if (size == capacity) {
            throw new IllegalStateException();
        }
        backIndex = (backIndex + 1) % capacity;
        array[backIndex] = work;
        size++;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[frontIndex];
    }

    @Override
    public E peek(int i) {
        i += frontIndex;
        if ((i < 0 || i > size - 1) && array[i] == null) {
            throw new NoSuchElementException();
        }
        return array[i];
    }

    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E temp = peek();
        array[frontIndex] = null;
        frontIndex = (frontIndex + 1) % capacity;
        size--;
        return temp;
    }

    @Override
    public void update(int i, E value) {
        i += frontIndex;
        if (i < 0 || i > size - 1) {
            throw new NoSuchElementException();
        }
        array[i] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        frontIndex = 0;
        backIndex = 0;
        size = 0;
        array = (E[])new Comparable[capacity];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        if (this.equals(other)) {
            return 0;
        }
        int size = this.size;
        int result = -1;
        if (size > other.size()) {
            size = other.size();
            result = 1;
        }
        for (int i = 0; i < size; i++) {
            if (peek(i).compareTo(other.peek(i)) > 0) {
                return 1;
            }
            if (peek(i).compareTo(other.peek(i)) < 0) {
                return -1;
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (this.size != other.size()) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (!peek(i).equals(other.peek(i))) {
                    return false;
                }
            }
            return true;
            // Your code goes here
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int c = 0;
        int result = peek(0).hashCode();
        for (int i = 0; i < size; i++) {
            c += peek(i).hashCode() * i;
        }
        result = 31 * result + c;
        return result;
    }
}
