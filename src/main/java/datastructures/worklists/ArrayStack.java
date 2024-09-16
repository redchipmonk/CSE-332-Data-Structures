package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private int capacity;
    private E[] array;
    private int size;
    public ArrayStack() {
        capacity = 10;
        array = (E[])new Object[capacity];

    }

    @Override
    public void add(E work) {
        array[size] = work;
        size++;
        expandCapacity();
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return array[size - 1];
    }

    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E temp = peek();
        size--;
        array[size] = null;
        return temp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        capacity = 10;
        array = (E[])new Object[capacity];
        size = 0;
    }
    private void expandCapacity() {
        if (size >= capacity * .9) {
            capacity *= 2;
            E[] temp = (E[])new Object[capacity];
            for (int i = 0; i < size; i++) {
                temp[i] = array[i];
            }
            array = temp;
        }
    }
}
