#!/usr/local/bin/python
# encoding: utf-8


class QueueItem(object):
    data = None
    next = None

    def __init__(self, s):
        self.data = s


class QueueOfStrings(object):
    def enqueue(self, item):
        raise NotImplementedError

    def dequeue(self):
        raise NotImplementedError

    def is_empty(self):
        raise NotImplementedError


class LinkedListQueue(QueueOfStrings):
    first = None
    last = None

    def enqueue(self, item):
        if isinstance(item, basestring):
            item = QueueItem(item)

        if self.is_empty():
            self.first = item
            self.last = item
        else:
            oldlast = self.last
            oldlast.next = item
            self.last = item

    def dequeue(self):
        item = self.first
        self.first = self.first.next
        if self.is_empty():
            self.last = None
        return item.data

    def is_empty(self):
        return self.first is None


class AtrrayQueue(QueueOfStrings):
    """
    okay, let's play in Java and try to avoid standard methods of list.
    """
    head = 0
    tail = 0

    def __init__(self, capacity):
        self.capacity = capacity
        self.array = [None] * capacity

    def enqueue(self, item):
        self.array[self.tail] = item
        self.tail += 1

    def dequeue(self):
        item = self.array[self.head]
        self.array[self.head] = None
        self.head += 1
        return item

    def size(self):
        return self.tail - self.head + 1

    def is_empty(self):
        return self.head == self.tail


class ResizableAtrrayQueue(AtrrayQueue):
    def __init__(self):
        self.array = [None]

    def enqueue(self, item):
        if self.tail + 1 == len(self.array):
            self.expand_right(self.size())
        super(ResizableAtrrayQueue, self).enqueue(item)
        print self.array

    def dequeue(self):
        item = super(ResizableAtrrayQueue, self).dequeue()
        if self.head == self.size() / 2:
            self.strip_left()
        print self.array
        return item

    def is_empty(self):
        return self.n == 0

    def strip_left(self):
        self.array = self.array[self.head:self.tail+1]
        self.tail = self.tail - self.head
        self.head = 0

    def expand_right(self, size):
        self.array += [None] * size


def solution(data_as_string, alg_class, capacity=None):
    data = data_as_string.split(' ')
    queue = alg_class(capacity) if capacity else alg_class()
    popped_data = []
    for string in data:
        if string == '-':
            popped_data.append(queue.dequeue())
        else:
            queue.enqueue(string)
    return popped_data


if __name__ == '__main__':
    res = solution('to be or not to - be - - that - - - is', LinkedListQueue)
    print res == 'to be or not to be'.split(), res

    res = solution('to be or not to - be - - that - - - is', AtrrayQueue, 10)
    print res == 'to be or not to be'.split(), res

    res = solution('to be or not to - be - - that - - - is', ResizableAtrrayQueue)
    print res == 'to be or not to be'.split(), res
