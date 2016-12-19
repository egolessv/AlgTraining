import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * A implementation of randomized queue
 *
 * @author Xu Yu
 * @since 2015-10-12
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    /** initial value of array size */
    private static final int INITIAL_CAPACITY = 4;
    /** array for storing items*/
    private Item[] mItems;
    /** size of array */
    private int size;

    public RandomizedQueue() {
        mItems = (Item[]) new Object[INITIAL_CAPACITY];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Add item into queue, resize queue if necessary.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == mItems.length) {
            resize(2 * mItems.length);
        }

        mItems[size++] = item;
    }

    /**
     * Remove a random item from queue, resize queue if necessary.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int r = StdRandom.uniform(size);
        Item item = mItems[r];
        mItems[r] = mItems[--size];
        mItems[size] = null;
        if (size > 0 && size == mItems.length/4) {
            resize(mItems.length/2);
        }
        return item;
    }

    /**
     * Return a random item from queue.
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int r = StdRandom.uniform(size);
        return mItems[r];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = mItems[i];
        }
        mItems = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] mItemCopy;

        private int mSizeCopy;

        private int current;

        /**
         * Construct a iterator with random order based on queue.
         */
        public RandomizedQueueIterator() {
            current = 0;
            mSizeCopy = size;
            mItemCopy = (Item[]) new Object[mSizeCopy];
            for (int i = 0; i < mSizeCopy; i++) {
                mItemCopy[i] = mItems[i];
                int r = StdRandom.uniform(i+1);
                Item temp = mItems[i];
                mItemCopy[i] = mItemCopy[r];
                mItemCopy[r] = temp;
            }
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return mItemCopy[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}
