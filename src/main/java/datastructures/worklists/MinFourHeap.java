package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    private Comparator<E> comparator;
    public MinFourHeap(Comparator<E> c) {
        capacity = 10;
        size = 0;
        data = (E[])new Object[capacity];
        comparator = c;
    }

    @Override
    public boolean hasWork() {
        return size != 0;
    }

    @Override
    public void add(E work) {
        if (work == null) {
            throw new IllegalArgumentException();
        }
        data[size] = work;
        size++;
        percolateUp(size - 1);
        expandCapacity();
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        E work = peek();
        if (size == 1) {
            data[0] = null;
            size--;
            return work;
        }
        E last = data[size - 1];
        data[0] = last;
        percolateDown(0);
        data[size - 1] = null;
        size--;
        return work;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public void clear() {
        capacity = 10;
        size = 0;
        data = (E[])new Object[capacity];
    }
    private void percolateUp(int i) {
        if (i == 0) {
            return;
        }
        int parent = (i - 1) / 4;
        E current = data[i];
        while (i >= 1 && comparator.compare(data[i], data[parent]) < 0) {
            data[i] = data[parent];
            data[parent] = current;
            i = parent;
            parent = (i - 1) / 4;
        }
    }
    private void percolateDown(int i) {
        int[] childrenArray = new int[4];
        for (int j = 0; j < childrenArray.length; j++) {
            childrenArray[j] = i * 4 + j + 1;
        }
        E current = data[i];
        while (childrenArray[0] < size) {
            int toSwap = childrenArray[0];
            for (int j = 1; j < childrenArray.length; j++) {
                if (childrenArray[j] < size) {
                    if (comparator.compare(data[childrenArray[j]], data[toSwap]) < 0) {
                        toSwap = childrenArray[j];
                    }
                }
            }
            if (comparator.compare(data[toSwap], current) < 0) {
                data[i] = data[toSwap];
                data[toSwap] = current;
                i = toSwap;
                for (int j = 0; j < childrenArray.length; j++) {
                    childrenArray[j] = i * 4 + j + 1;
                }
            }
            else {
                return;
            }
        }
    }
    private void expandCapacity() {
        if (size >= capacity * .9) {
            capacity *= 2;
            E[] temp = (E[])new Object[capacity];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
    }
}
