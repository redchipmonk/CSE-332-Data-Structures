package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private ListNode front;
    private ListNode back;
    private int size;
    public ListFIFOQueue() {
        front = null;
        back = null;
        size = 0;
    }

    @Override
    public void add(E work) {
        ListNode current = new ListNode(work);
        if (back == null) {
            back = current;
            front = current;
            size++;
            return;
        }
        back.next = current;
        back = current;
        size++;
    }

    @Override
    public E peek() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        ListNode current = front;
        front = front.next;
        if (front == null) {
            back = null;
        }
        size--;
        return current.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
        back = null;
        size = 0;
    }

    private class ListNode {

        public E data;
        public ListNode next;

        public ListNode(E data) {
            this.data = data;
            next = null;
        }

    }

}
