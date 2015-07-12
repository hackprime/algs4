#!/usr/local/bin/python
# encoding: utf-8

from binary_heap import Heap


class HeapSort(object):
    def sort(self, heap):
        # heap-ordering
        for k in xrange(heap.n / 2, 0, -1):
            heap.sink(k, heap.n)

        # sorting
        n = heap.n
        while n > 1:
            heap.pq[1], heap.pq[n] = heap.pq[n], heap.pq[1]
            n -= 1
            heap.sink(1, n)
        return heap


def test():
    h = Heap()
    h.data_from_string('SORTEXAMPLE')
    print 'initial', h

    s = HeapSort()

    sorted_heap = s.sort(h)
    assert unicode(sorted_heap) == 'A E E L M O P R S T X', h
    print 'sort ok'

if __name__ == '__main__':
    test()
