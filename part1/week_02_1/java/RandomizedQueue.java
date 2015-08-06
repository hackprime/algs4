// Randomized queue. A randomized queue is similar to a stack or queue, except that the item
// removed is chosen uniformly at random from items in the data structure.
// Create a generic data type RandomizedQueue that implements the following API

// Corner cases.
// - The order of two or more iterators to the same randomized queue
//   must be mutually independent; each iterator must maintain its own random order.
// - Throw a java.lang.NullPointerException if the client attempts to add a null item;
// - throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item
//   from an empty randomized queue;
// - throw a java.lang.UnsupportedOperationException if the client calls the remove() method
//   in the iterator;
// - throw a java.util.NoSuchElementException if the client calls the next() method in the iterator
//   and there are no more items to return.

// Performance requirements.
// - Your randomized queue implementation must support each randomized queue operation
//   (besides creating an iterator) in constant amortized time and use space
//   proportional to the number of items currently in the queue.
//   That is, any sequence of M randomized queue operations (starting from an empty queue)
//   should take at most cM steps in the worst case, for some constant c.
// - Additionally, your iterator implementation must support operations next() and hasNext()
//   in constant worst-case time; and construction in linear time; you may use a linear
//   amount of extra memory per iterator.

// java -cp $CLASSPATH:`pwd` RandomizedQueue


import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int count;
    private class Node<Item> {
        public Item item;
        public Node<Item> next;
        public Node<Item> prev;

        public Node(Item item, Node<Item> next, Node<Item> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    private class RandomListIterator implements Iterator<Item> {
        private int current = 0;
        private int[] order;
        public RandomListIterator() {
            order = new int[count];
            for (int i = 0; i < count; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }
        public boolean hasNext() {
            return current < count;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Node<Item> node = getNode(order[current]);
            current++;
            return node.item;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    private Node<Item> randomNode() {
        return getNode(StdRandom.uniform(0, count));
    }
    private Node<Item> getNode(int index) {
        int currentIndex = 0;
        Node<Item> node = first;
        while (currentIndex < index) {
            node = node.next;
            currentIndex++;
        }
        return node;
    }
    public RandomizedQueue() {
        // construct an empty randomized queue
        count = 0;
        first = null;
        last = null;
    }
    public boolean isEmpty() {
        // is the queue empty?
        return count == 0;
    }
    public int size() {
        // return the number of items on the queue
        return count;
    }
    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> newnode = new Node<Item>(item, first, null);
        first = newnode;
        count++;
    }
    public Item dequeue() {
        // remove and return a random item
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node<Item> node = randomNode();
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        count--;
        return node.item;
    }
    public Item sample() {
        // return (but do not remove) a random item
        if (count == 0) {
            throw new java.util.NoSuchElementException();
        }
        return randomNode().item;
    }
    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new RandomListIterator();
    }
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        System.out.println("-isempty " + rq.isEmpty());

        rq.enqueue("one");
        rq.enqueue("two");
        rq.enqueue("three");
        rq.enqueue("fout");
        System.out.println("-initial " + rq.size());

        System.out.println("-dequeue " + rq.dequeue());

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

