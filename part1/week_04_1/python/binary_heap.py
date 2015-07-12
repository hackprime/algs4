#!/usr/local/bin/python
# encoding: utf-8


class Heap(object):
    pq = [None]

    def data_from_string(self, heap_as_string):
        self.pq = [None] + list(heap_as_string)

    @property
    def n(self):
        return len(self.pq) - 1

    def insert(self, item):
        self.pq.append(item)
        self.swim(self.n)

    def swim(self, key):
        while key > 1 and self.pq[key/2] < self.pq[key]:
            self.pq[key], self.pq[key/2] = self.pq[key/2], self.pq[key]
            key /= 2

    def sink(self, key, n=None):
        if n is None:
            n = self.n
        while 2 * key <= n:
            j = 2 * key
            if j < n and self.pq[j] < self.pq[j + 1]:
                j += 1
            if self.pq[key] >= self.pq[j]:
                break
            self.pq[key], self.pq[j] = self.pq[j], self.pq[key]
            key = j

    def del_max(self):
        max_item = self.pq[1]
        self.pq[1], self.pq[self.n] = self.pq[self.n], self.pq[1]
        self.pq.pop()
        self.sink(1)
        return max_item

    def as_string(self):
        return ' '.join(self.pq[1:])

    def __repr__(self):
        return self.as_string()

    def __unicode__(self):
        return self.as_string()


def test_heap():
    data = 'T P R N H O A E I G'
    h = Heap()
    h.data_from_string(data.replace(' ', ''))
    # print 'initial', h

    h.insert('S')
    assert unicode(h) == 'T S R N P O A E I G H', h
    # print 'insert done'

    maximum = h.del_max()
    assert unicode(h) == 'S P R N H O A E I G', h
    assert maximum == 'T', maximum
    # print 'del max done'

    maximum = h.del_max()
    assert unicode(h) == 'R P O N H G A E I', h
    assert maximum == 'S', maximum
    # print 'del max done'

    h.insert('S')
    assert unicode(h) == 'S R O N P G A E I H', h
    # print 'insert done'


if __name__ == '__main__':
    test_heap()

    h = Heap()
    h.data_from_string('91 72 65 71 51 12 22 52 57 33'.split())
    for c in '50 41 83'.split():
        h.insert(c)
    print unicode(h)

    h = Heap()
    h.data_from_string('96 90 93 87 16 10 22 26 60 14'.split())
    for x in xrange(3):
        h.del_max()
    print unicode(h)
