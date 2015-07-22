#!/usr/local/bin/python
# encoding: utf-8

class IntervalSearchTree(object):
    def put(self, lo, hi, val):
        """Put interval-value pair into ST"""
        pass

    def get(self, lo, hi):
        """Value paired with given interval"""
        pass

    def delete(self, lo, hi):
        """delete the given interval"""
        pass

    def intersects(self, lo, hi):
        """All intervals that intersect the given interval"""
        x = self.root
        while x is not None:
            if x.interval.intersects(lo, hi):
                return x.interval
            elif x.left is None:
                x = x.right
            elif x.left.max < lo:
                x = x.right
            else:
                x = x.left
        return None


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
