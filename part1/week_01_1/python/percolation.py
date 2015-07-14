#!/usr/local/bin/python
# encoding: utf-8

import random
class UF(object):
    pass

class Percolation(object):
    percolation_result = False

    def __init__(self, n):
        """create N-by-N grid, with all sites blocked"""
        self.items_count = n * n
        self.uf = UF(self.items_count)
        self.top_index = self.items_count
        self.bottom_index = self.items_count + 1

    def open(self, i, j):

        pass

    def is_open(self, i, j):
        """is site (row i, column j) open?"""
        pass

    def is_full(self, i, j):
        """is site (row i, column j) full?"""
        pass

    def percolates(self):
        """does the system percolate?"""
        pass

    def ij_ti_index(self, index):
        return self.n * (i - 1) + j

    def index_to_ij(self, index):
        return index / self.n, index % self.n

    def visualize(self):
        pass



def solution(n, t):
    p = Percolation(n)

    sites = random.sample(range(n * n), t)
    for site in sites:
        p.open(site)
        if p.percolates():
            p.percolation_result = True
            break

    return p


if __name__ == '__main__':
    print solution(20, 30).visualize()
