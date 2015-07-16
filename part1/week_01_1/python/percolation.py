#!/usr/local/bin/python
# encoding: utf-8
from union_find import WeightedQuickFindUF
import random

import random


class Percolation(object):
    percolation_result = False

    def __init__(self, n):
        """create N-by-N grid, with all sites blocked"""
        self.n = n
        self.items_count = n * n
        self.uf = WeightedQuickFindUF(self.items_count + 2)
        self.top_index = self.items_count
        self.bottom_index = self.items_count + 1

        self.open_sites = [False] * (self.items_count + 2)
        self.open_sites[-1] = True
        self.open_sites[-2] = False

    def open_by_index(self, index):
        self.open(*self.index_to_ij(index))

    def open(self, i, j):
        index = self.ij_to_index(i, j)
        self.open_sites[index] = True
        self.check_nodes_around(i, j)
        if i == 0:
            self.uf.union(index, self.top_index)
        elif i == self.n - 1:
            self.uf.union(index, self.bottom_index)

    def check_nodes_around(self, i, j):
        item_index = self.ij_to_index(i, j)

        # bottom
        if i > 0 and self.is_open(i - 1, j):
            bottom_index = self.ij_to_index(i - 1, j)
            self.uf.union(item_index, bottom_index)
        # top
        if i < self.n - 1 and self.is_open(i + 1, j):
            top_index = self.ij_to_index(i + 1, j)
            self.uf.union(item_index, top_index)
        # left
        if j > 0 and self.is_open(i, j - 1):
            left_index = self.ij_to_index(i, j - 1)
            self.uf.union(item_index, left_index)
        # right
        if j < self.n - 1 and self.is_open(i, j + 1):
            right_index = self.ij_to_index(i, j + 1)
            self.uf.union(item_index, right_index)

    def is_open(self, i, j):
        """is site (row i, column j) open?"""
        return self.ij_to_index(i, j) in self.open_sites

    def is_full(self, i, j):
        """is site (row i, column j) full?"""
        return self.uf.connected(self.top_index, self.index(i, j))

    def percolates(self):
        """does the system percolate?"""
        return self.uf.connected(self.top_index, self.bottom_index)

    def ij_to_index(self, i, j):
        return self.n * i + j

    def index_to_ij(self, index):
        return index / self.n, index % self.n

    def visualize(self):
        return [['0' if self.is_open(i, j) else 'X' for j in xrange(self.n)] for i in xrange(self.n)]


# def ij_to_index(n, i, j):
#     return n * i + j

# def index_to_ij(n, index):
#     return index / n, index % n

def solution(n, t):
    p = Percolation(n)

    sites = random.sample(range(n * n), t)
    for site in sites:
        p.open_by_index(site)
        print 'opening site %d' % site
        if p.percolates():
            p.percolation_result = True
            print 'system percolates!'
            break

    return p


if __name__ == '__main__':
    for line in solution(20, 30).visualize():
        print line


class IndexOutOfBoundsException(Exception):
    pass


class IllegalArgumentException(Exception):
    pass
