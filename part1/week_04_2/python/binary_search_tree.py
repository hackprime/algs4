#!/usr/local/bin/python
# encoding: utf-8


import os
import sys
from collections import deque, OrderedDict

QUEUE_DIR = os.path.join(os.path.dirname(os.path.dirname(
        os.path.dirname(os.path.abspath(__file__)))), 'week_02_1/python')
sys.path.append(QUEUE_DIR)
from queue import ResizableAtrrayQueue as Queue


class Node(object):
    key = None
    value = None
    left = None
    right = None
    count = None

    def __init__(self, key, value=None):
        self.key = key
        self.value = value
        self.count = 0


class BinarySearchTree(object):
    root = None
    _keys = []

    @classmethod
    def from_lot(cls, keys, values=None):
        if not keys:
            return
        if isinstance(keys, OrderedDict):
            keys, values = keys.keys(), keys.values()
        elif not values:
            values = [None] * len(keys)
        tree = cls()
        for k, v in zip(keys, values):
            tree.put(k, v)
        return tree

    def as_lot(self):
        tree_lot = []
        if not self.root:
            return tree_lot
        current_level, next_level = deque(), deque()
        current_level.append(self.root)

        while current_level:
            current_node = current_level.popleft()
            if current_node:
                tree_lot.append(current_node.key)
                next_level.append(current_node.left)
                next_level.append(current_node.right)
            if not current_level:
                current_level, next_level = next_level, current_level
        return tree_lot

    def put(self, key, value=None):
        self.root = self._put(key, value, self.root)

    def _put(self, key, value=None, node=None):
        if node is None:
            return Node(key, value)
        if key < node.key:
            node.left = self._put(key, value, node.left)
        elif key > node.key:
            node.right = self._put(key, value, node.right)
        else:
            node.value = value

        node.count = 1 + self._size(node.left) + self._size(node.right)

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

    def _delete(self, key, node):
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

if __name__ == '__main__':
    inp = map(int, '64 55 89 50 63 76 96 28 74 81'.split())
    bst = BinarySearchTree.from_lot(inp)
    res = bst.as_lot()
    print res == inp, res
