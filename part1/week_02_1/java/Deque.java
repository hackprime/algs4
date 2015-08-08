// Dequeue. A double-ended queue or deque (pronounced "deck")
// is a generalization of a stack and a queue that supports adding
// and removing items from either the front or the back
// of the data structure. Create a generic data type Deque
// that implements the following API.

// Corner cases.
// - Throw a java.lang.NullPointerException
//   if the client attempts to add a null item;
// - throw a java.util.NoSuchElementException
//   if the client attempts to remove an item from an empty deque;
// - throw a java.lang.UnsupportedOperationException
//   if the client calls the remove() method in the iterator;
// - throw a java.util.NoSuchElementException if the client calls the next()
//   method in the iterator and there are no more items to return.

// Performance requirements.
// - Your deque implementation must support each deque operation
//   in constant worst-case time and use space proportional to the number of items
//   currently in the deque.
// - Additionally, your iterator implementation must support each operation
//   (including construction) in constant worst-case time.

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int count;

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item item, Node<Item> next, Node<Item> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
    public Deque() {
        // construct an empty deque
        count = 0;
        first = null;
        last = null;
    }
    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }
    public int size() {
        // return the number of items on the deque
        return count;
    }
    public void addFirst(Item item) {
        // add the item to the front
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> newnode = new Node<Item>(item, first, null);
        if (first != null) {
            first.prev = newnode;
        }
        first = newnode;
        if (last == null) {
            last = first;
        }
        count++;
    }
    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> newnode = new Node<Item>(item, null, last);
        if (last != null) {
            last.next = newnode;
        }
        last = newnode;
        if (first == null) {
            first = last;
        }
        count++;
    }
    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) {
            throw new java.lang.NullPointerException();
        }
        Item item = first.item;
        if (first.next == null) {
            last = null;
            first = null;
        } else {
            first.next.prev = null;
        }
        first = first.next;
        count--;
        return item;
    }
    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) {
            throw new java.lang.NullPointerException();
        }
        Item item = last.item;
        if (last.prev == null) {
            last = null;
            first = null;
        } else {
            last.prev.next = null;
        }
        last = last.prev;

        count--;
        return item;
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();

        if (!StdIn.isEmpty()) {
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
                d.addFirst(s);
            }
            d.removeFirst();
            d.addFirst("one");
            d.removeLast();
            d.addLast("three");

            System.out.println("result size: " + d.size());
            for (String s : d) {
                System.out.println(s);
            }
        }
    }
}
