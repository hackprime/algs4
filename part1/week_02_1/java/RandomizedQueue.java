// Randomized queue. A randomized queue is similar to a stack or queue, except that the item
// removed is chosen uniformly at random from items in the data structure.
// Create a generic data type RandomizedQueue that implements the following API

// Corner cases. The order of two or more iterators to the same randomized queue
// must be mutually independent; each iterator must maintain its own random order.
// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item
// from an empty randomized queue; throw a java.lang.UnsupportedOperationException
// if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
// and there are no more items to return.

// Performance requirements. Your randomized queue implementation must support
// each randomized queue operation (besides creating an iterator) in constant amortized time
// and use space proportional to the number of items currently in the queue.
// That is, any sequence of M randomized queue operations (starting from an empty queue)
// should take at most cM steps in the worst case, for some constant c.
// Additionally, your iterator implementation must support operations next() and hasNext()
// in constant worst-case time; and construction in linear time; you may use a linear
// amount of extra memory per iterator.

public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {
        // construct an empty randomized queue
    }
    public boolean isEmpty() {
        // is the queue empty?
    }
    public int size() {
        // return the number of items on the queue
    }
    public void enqueue(Item item) {
        // add the item
    }
    public Item dequeue() {
        // remove and return a random item
    }
    public Item sample() {
        // return (but do not remove) a random item
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
    }

    public static void main(String[] args) {
        // unit testing
    }
}

