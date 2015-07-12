#!/usr/local/bin/python
# encoding: utf-8


class AbstractSymbolTable(object):
    def put(self, key, value):
        raise NotImplementedError

    def get(self, key):
        raise NotImplementedError

    def delete(self, key):
        raise NotImplementedError

    def contains(self, key):
        raise NotImplementedError

    def is_empty(self):
        raise NotImplementedError

    def size(self):
        raise NotImplementedError

    def keys(self, from=None, until=None):
        raise NotImplementedError

    # advanced

    def min(self):
        raise NotImplementedError

    def max(self):
        raise NotImplementedError

    def floor(self, key):
        """
        Largest key less than or equal to key
        """
        raise NotImplementedError

    def ceiling(self, key):
        """
        Smallest key greather than or equal to key
        """
        raise NotImplementedError

    def rank(self, key):
        """
        Number of keys less than key
        """
        raise NotImplementedError

    def select(self, k):
        """
        Key of rank k
        """
        raise NotImplementedError

    def delete_min(self):
        raise NotImplementedError

    def delete_max(self):
        raise NotImplementedError


class SymbolTable(AbstractSymbolTable):
    _keys = []
    _values = []

    @property
    def n(self):
        return len(self._keys)

    def contains(self, key):
        return self.get(key) is not None

    def delete(self, key):
        """lazy version"""
        self.put(key, None)

    def get(self, key):
        if self.is_empty():
            return None
        i = self.rank(key)
        if i < self.n and self._keys[i] == key:
            return self._values[i]
        return None


    def rank(self, key):
        start = 0
        end = self.n - 1
        while start <= end:
            mid = start + (end - start) / 2
            if key < self._keys[mid]:
                end = mid - 1
            elif key > self._keys[mid]:
                start = mid + 1
            else:
                return mid
        return start


#     pq = [None]

#     def data_from_string(self, heap_as_string):
#         self.pq = [None] + list(heap_as_string)

#     @property
#     def n(self):
#         return len(self.pq) - 1

#     def insert(self, item):
#         self.pq.append(item)
#         self.swim(self.n)

#     def swim(self, key):
#         while key > 1 and self.pq[key/2] < self.pq[key]:
#             self.pq[key], self.pq[key/2] = self.pq[key/2], self.pq[key]
#             key /= 2

#     def sink(self, key, n=None):
#         if n is None:
#             n = self.n
#         while 2 * key <= n:
#             j = 2 * key
#             if j < n and self.pq[j] < self.pq[j + 1]:
#                 j += 1
#             if self.pq[key] >= self.pq[j]:
#                 break
#             self.pq[key], self.pq[j] = self.pq[j], self.pq[key]
#             key = j

#     def del_max(self):
#         max_item = self.pq[1]
#         self.pq[1], self.pq[self.n] = self.pq[self.n], self.pq[1]
#         self.pq.pop()
#         self.sink(1)
#         return max_item

#     def as_string(self):
#         return ' '.join(self.pq[1:])

#     def __repr__(self):
#         return self.as_string()

#     def __unicode__(self):
#         return self.as_string()


def test():
    keys = 'S E A R C H E X A M P L E'.split()
    values = range(13)

    st = SymbolTable()
    for key, value in zip(keys, values):
        st.put(key, value)

    assert st.keys() == sorted(keys), st.keys()
#     # print 'insert done'

#     maximum = h.del_max()
#     assert unicode(h) == 'S P R N H O A E I G', h
#     assert maximum == 'T', maximum
#     # print 'del max done'

#     maximum = h.del_max()
#     assert unicode(h) == 'R P O N H G A E I', h
#     assert maximum == 'S', maximum
#     # print 'del max done'

#     h.insert('S')
#     assert unicode(h) == 'S R O N P G A E I H', h
#     # print 'insert done'


def frequency_counter(data, min_len, cls):
    st = SymbolTable()

    while not st.is_empty():
        word = data.pop()
        if len(word) < min_len:
            continue

        if st.contains(word):
            st.put(word, 1)
        else:
            st.put(word, st.get(word) + 1)

    max_word = ''
    st.put(max_word, 0)

    for word in st.keys():
        if st.get(word) > st.get(max_word):
            max_word = word

    return "%s:%s" % (max_word, st.get(max_word))


if __name__ == '__main__':
    test()

    frequency_counter()

#     h = Heap()
#     h.data_from_string('91 72 65 71 51 12 22 52 57 33'.split())
#     for c in '50 41 83'.split():
#         h.insert(c)
#     print unicode(h)

#     h = Heap()
#     h.data_from_string('96 90 93 87 16 10 22 26 60 14'.split())
#     for x in xrange(3):
#         h.del_max()
#     print unicode(h)
