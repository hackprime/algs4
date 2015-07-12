#!/usr/local/bin/python
# encoding: utf-8

import os
import sys
QUEUE_DIR = os.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
                    'week_02_1/python')
sys.path.append(QUEUE_DIR)

from queue import ResizableAtrrayQueue as Queue


class Node(object):
    key = None
    value = None
    left = None
    right = None
    count = None

    def __init__(self, key, value):
        self.key = key
        self.value = value


class BinarySearchTree(object):
    root = None
    _keys = []

    def put(self, key, value):
        self.root = self.root._put(key, value, self.root)

    def _put(self, key, value, node=None):
        if node is None:
            return Node(key, value)
        if key < node.key:
            node.left = self._put(key, value, node.left)
        elif key > node.key:
            node.right = self._put(key, value, node.right)
        else:
            node.value = value

        node.count = 1 + self.size(node.left) + self.size(node.right)

        return node

    def get(self, key):
        current = self.root
        while current is not None:
            if key < self.root.key:
                current = self.root.left
            elif key > self.root.key:
                current = self.root.right
            else:
                return current.value
        return None

    def delete(self, key):
        pass

    def floor(self, key):
        node = self._floor(key, self.root)
        if node is None:
            return None
        return node

    def _floor(self, key, node):
        if node is None:
            return None
        if key == node.key:
            return node
        if key < node.key:
            return self._floor(key, node.left)

        t = self._floor(key, node.right)
        return t if t else node

    def size(self):
        return self._size(self.root)

    def _size(self, node):
        return 0 if node is None else node.count

    def rank(self, key):
        return self._rank(key, self.root)

    def _rank(self, key, node):
        if node is None:
            return 0

        if key < node.key:
            return self._rank(key, node.left)
        elif key > node.key:
            return 1 + self._size(node.left) + self._rank(key, node.right)
        else:
            return self._size(node.left)

    def __iter__(self):
        q = Queue()
        self.inorder(self.root, q)
        return q

    def inorder(self, node, queue):
        if node is None:
            return node
        self.inorder(node.left, queue)
        queue.enqueue(node.key)
        self.inorder(node.right, queue)

    def delete_min(self):
        self.root = self._delete_min(self.root)

    def _delete_min(self, node):
        if node is None:
            return node.right
        node.left = self.delete_min(node.left)
        node.count = 1 + self.size(node.left) + self.size(node.right)
        return node

    def delete_max(self):
        self.root = self._delete_max(self.root)

    def _delete_max(self, node):
        if node is None:
            return node.left
        node.right = self.delete_max(node.right)
        node.count = 1 + self.size(node.left) + self.size(node.right)
        return node

    def delete(self, key):
        self.root = self._delete(key, self.root)

    def delete(self, key, node):
        if node is None:
            return None
        if key < node.key:
            node.left = self._delete(key, node.left)
        elif key > node.key:
            node.right = self._delete(key, node.right)
        else:
            if node.right is None:
                return node.left
            if node.left is None:
                return node.right

            t = node
            node = self._min(t.right)
            node.right = self._delete_min(t.right)
            node.left = t.left

        node.count = 1 + self.size(node.left) + self.size(node.right)
        return node

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
