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
    private Node first;
    private Node last;
    private int count;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    private class ListIterator implements Iterator<Item> {
        private Node current;
        public ListIterator(Node initialNode) {
            current = initialNode;
        }
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
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
        if (count == 0) {
            first = new Node();
            first.item = item;
            last = first;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        count++;
    }
    public void addLast(Item item) {
        // add the item to the end
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node newnode = new Node();
        newnode.item = item;
        if (last != null) {
            newnode.prev = last;
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
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        if (count == 1) {
            last = null;
            first = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }
        count--;
        return item;
    }
    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if (last.prev == null) {
            last = null;
            first = null;
        } else {
            last.prev.next = null;
            last = last.prev;
        }
        count--;
        return item;
    }
    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator(first);
    }
}
