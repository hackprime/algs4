// Randomized queue. A randomized queue is similar to a stack or queue,
// except that the item removed is chosen uniformly at random from items
// in the data structure. Create a generic data type RandomizedQueue
// that implements the following API

// Corner cases.
// - The order of two or more iterators to the same randomized queue
//   must be mutually independent; each iterator must maintain its own random order.
// - Throw a java.lang.NullPointerException
//   if the client attempts to add a null item;
// - throw a java.util.NoSuchElementException if the client attempts to sample
//   or dequeue an item from an empty randomized queue;
// - throw a java.lang.UnsupportedOperationException
//   if the client calls the remove() method in the iterator;
// - throw a java.util.NoSuchElementException if the client calls the next() method
//   in the iterator and there are no more items to return.

// Performance requirements.
// - Your randomized queue implementation must support each randomized
//   queue operation (besides creating an iterator) in constant amortized time
//   and use space proportional to the number of items currently in the queue.
//   That is, any sequence of M randomized queue operations
//   (starting from an empty queue) should take at most cM steps in the worst case,
//   for some constant c.
// - Additionally, your iterator implementation must support operations next()
//   and hasNext() in constant worst-case time; and construction in linear time;
//   you may use a linear
//   amount of extra memory per iterator.

// java -cp $CLASSPATH:`pwd` RandomizedQueue

// structure
//     array

// grow order
//     double size of array is array is full

// shrink
//     halve size if actual array size equals N/3

// randomized dequeue
//     uniformly pop an item from array and set array cell to null
//     if uniformly seelted array cell is null ->
//         move to next element until not null of tail is reached
//         if tail is reached -> move left using the same way

import java.util.Iterator;

public class ArrayRandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int capacity;
    private Item[] array;

    public ArrayRandomizedQueue() {
        array = (Item[]) new Object[1];
        size = 0;
        capacity = 1;
    }

    private class RandomListIterator implements Iterator<Item> {
        private int current;
        private int initialSize;
        private int[] order;

        public RandomListIterator() {
            initialSize = size;
            current = 0;
            order = new int[size];
            for (int i = 0; i < size; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }
        public boolean hasNext() {
            return current < size;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = array[order[current]];
            if (size() != initialSize) {
                throw new java.util.ConcurrentModificationException();
            }
            current++;
            return item;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private void shrinkCapacity() {
        capacity /= 2;
        Item[] newArray = (Item[]) new Object[capacity / 2];
        int i = 0;
        for (Item item : array) {
            newArray[i++] = item;
        }
        array = newArray;
    }

    private void expandCapacity() {
        capacity *= 2;
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i <= size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (size + 1 == capacity) {
            expandCapacity();
        }
        array[size++] = item;
    }

    public Item dequeue() {
       // remove and return a random item
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(0, size);
        Item item = array[randomIndex];
        size--;
        array[randomIndex] = array[size];
        array[size] = null;

        if (capacity / 2 == size) {
            shrinkCapacity();
        }
        return item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Item sample() {
        // return (but do not remove) a random item
        if (size() == 0) {
            throw new java.util.NoSuchElementException();
        }
        return array[StdRandom.uniform(0, size)];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomListIterator();
    }

    public static void main(String[] args) {
        ArrayRandomizedQueue<String> rq = new ArrayRandomizedQueue<String>();

        System.out.println("-isempty " + rq.isEmpty());

        rq.enqueue("one");
        rq.enqueue("two");
        rq.enqueue("three");
        rq.enqueue("four");
        System.out.println("-initial " + rq.size());

        System.out.println("-dequeue " + rq.dequeue());

        for (String s : rq) {
            System.out.println("  " + s);
        }

        System.out.println("-dequeued size " + rq.size());

        System.out.println("-sample " + rq.sample());
        System.out.println("-sample " + rq.sample());
        System.out.println("-sample " + rq.sample());

        System.out.println("-random iterator");
        for (String s : rq) {
            System.out.println(s);
        }

        System.out.println("-random iterator one more time");
        for (String s : rq) {
            System.out.println(s);
        }
    }
}
