#!/usr/local/bin/python
# -*- coding: utf-8 -*-
from copy import deepcopy


class Board:
    n = None
    blocks = None

    def __init__(self, blocks):
        """
        construct a board from an N-by-N array of blocks
        (where blocks[i][j] = block in row i, column j)
        """
        self.blocks = blocks
        self.n = len(blocks)

    def dimension(self):
        return self.n

    def hamming(self):
        """number of blocks out of place"""
        hamming = 0
        for i in xrange(self.n):
            for j in xrange(self.n):
                target_value = self.target_value(i, j)
                if self.blocks[i][j] != target_value:
                    hamming += 1
        return hamming

    def manhattan(self):
        """sum of Manhattan distances between blocks and goal"""
        manhattan = 0
        for i in xrange(self.n):
            for j in xrange(self.n):
                t_i, t_j = self.target_place(self.blocks[i][j])
                manhattan += abs(t_i - i) + abs(t_j - j)
        return manhattan

    def target_value(self, i, j):
        return (i * self.n + j + 1) % (self.n * self.n)

    def target_place(self, value):
        if value == 0:
            return self.n - 1, self.n - 1
        return (value - 1) / self.n, (value - 1) % self.n

    def isGoal(self):
        """is this board the goal board?"""
        return self.manhattan() == 0

    def twin(self):
        """a board that is obtained by exchanging any pair of blocks"""
        twin_blocks = deepcopy(self.blocks)
        i, j = 0, 0
        if twin_blocks[i][j] != 0 and twin_blocks[i][j+1] != 0:
            self.swap(twin_blocks, i, j, i, j + 1)
        else:
            self.swap(twin_blocks, i + 1, j, i + 1, j+1)
        return Board(twin_blocks)

    def equals(self, y):
        """does this board equal y?"""
        return self.toString() == y.toString()

    def neighbors(self):
        """
        all neighboring boards
        public Iterable<Board> neighbors()
        """
        stack = []
        zero_i, zero_j = 0, 0
        for i in xrange(self.n):
            for j in xrange(self.n):
                if self.blocks[i][j] == 0:
                    zero_i = i
                    zero_j = j
                    break

        # left
        if zero_j != 0:
            b = deepcopy(self.blocks)
            self.swap(b, zero_i, zero_j, zero_i, zero_j - 1)
            # b[zero_i][zero_j], b[zero_i][zero_j - 1] = b[zero_i][zero_j - 1], b[zero_i][zero_j]
            stack.append(Board(b))

        # right
        if zero_j != self.n - 1:
            b = deepcopy(self.blocks)
            self.swap(b, zero_i, zero_j, zero_i, zero_j + 1)
            # b[zero_i][zero_j], b[zero_i][zero_j + 1] = b[zero_i][zero_j + 1], b[zero_i][zero_j]
            stack.append(Board(b))

        # top
        if zero_i != 0:
            b = deepcopy(self.blocks)
            self.swap(b, zero_i, zero_j, zero_i - 1, zero_j)
            # b[zero_i][zero_j], b[zero_i - 1][zero_j] = b[zero_i - 1][zero_j], b[zero_i][zero_j]
            stack.append(Board(b))

        # bottom
        if zero_i != self.n - 1:
            b = deepcopy(self.blocks)
            self.swap(b, zero_i, zero_j, zero_i + 1, zero_j)
            # b[zero_i][zero_j], b[zero_i + 1][zero_j] = b[zero_i + 1][zero_j], b[zero_i][zero_j]
            stack.append(Board(b))

        return stack

    def swap(self, b, i1, j1, i2, j2):
        b[i1][j1], b[i2][j2] = b[i2][j2], b[i1][j1]
        print b

    def toString(self):
        """string representation of this board (in the output format specified below)"""
        strings = ["%d" % self.n]
        for i in xrange(self.n):
            s = ""
            for j in xrange(self.n):
                s += "%2d " % self.blocks[i][j]
            strings.append(s)
        return "\n".join(strings)


def test_board(data):
    b = Board(data)
    # print b.hamming()
    # print b.manhattan()
    # print b.isGoal()
    # print b.toString()
    # print 'neighbors'
    # for i in b.neighbors():
    #     print i.toString()
    print b.twin().toString()

if __name__ == "__main__":
    data = [[1, 2, 3],
            [0, 4, 5],
            [7, 8, 6]]
    test_board(data)
