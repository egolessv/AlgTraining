import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A implementation of double-ended queue
 *
 * @author Xu Yu
 * @since 2015-10-12
 */
public class Deque<Item> implements Iterable<Item> {

    /** head pointer of deque */
    private Node head;
    /** tail pointer of deque */
    private Node tail;
    /** size of deque */
    private int size;
    /** node class for data storage */
    private class Node {
        private Node next;
        private Node prev;
        private Item item;
    }

    public Deque() {
    }

    /**
     * @return whether deque is empty
     */
    public boolean isEmpty() {
        return head == null || tail == null;
    }

    /**
     * @return size of deque
     */
    public int size() {
        return size;
    }

    /**
     * Add data at front.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node oldhead = head;
        head = new Node();
        head.item = item;
        head.prev = null;
        if (isEmpty()) {
            head.next = null;
            tail = head;
        } else {
            head.next = oldhead;
            oldhead.prev = head;
        }
        size++;
    }

    /**
     * Add data at end
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node oldtail = tail;
        tail = new Node();
        tail.item = item;
        tail.next = null;
        if (isEmpty()) {
            tail.prev = null;
            head = tail;
        } else {
            tail.prev = oldtail;
            oldtail.next = tail;
        }
        size++;
    }

    /**
     * Remove data at front.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = head.item;
        head = head.next;
        if (isEmpty()) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;

        return item;
    }

    /**
     * Remove data at end
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = tail.item;
        tail = tail.prev;
        if (isEmpty()) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
