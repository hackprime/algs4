#!/usr/local/bin/python
# encoding: utf-8

class UF(object):
    def __init__(self, n):
        self.n = n

    def union(a, b):
        raise NotImplementedError

    def connected(a, b):
        raise NotImplementedError


class QuickFindUF(UF):
    arr = None

    def __init__(self, n):
        super(QuickFindUF, self).__init__(n)
        self.n = n
        self.arr = range(n)

    def union(self, a, b):
        target_index = self.arr[a]
        for index in xrange(self.n):
            if self.arr[index] == target_index:
                self.arr[index] = self.arr[b]

    def connected(self, a, b):
        return self.arr[a] == self.arr[b]


class QuickUnionUF(QuickFindUF):
    def union(self, a, b):
        self.arr[self.root(self.arr[a])] = self.root(self.arr[b])

    def connected(self, a, b):
        return self.root(self.arr[a]) == self.root(self.arr[b])

    def root(self, a):
        while a != self.arr[a]:
            a = self.arr[a]
        return a


class WeightedQuickFindUF(QuickUnionUF):
    size = None

    def __init__(self, n):
        super(WeightedQuickFindUF, self).__init__(n)
        self.size = [1] * n

    def union(self, a, b):
        root_a = self.root(self.arr[a])
        root_b = self.root(self.arr[b])
        if self.size[root_a] < self.size[root_b]:
            self.arr[root_a] = root_b
            self.size[root_b] += self.size[root_a]
        else:
            self.arr[root_b] = root_a
            self.size[root_a] += self.size[root_b]


class PathCompressionWeightedQuickFindUF(WeightedQuickFindUF):
    def root(self, a):
        while a != self.arr[a]:
            self.arr[a] = self.arr[self.arr[a]]
            a = self.arr[a]
        return a


def solution(n, data_as_string, alg_class):
    data = [map(int, x.split('-')) for x in data_as_string.split(' ')]
    uf = alg_class(n)
    for x, y in data:
        if not uf.connected(x, y):
            # print 'Connect %d and %d' % (x, y)
            uf.union(x, y)
    return uf.arr

if __name__ == '__main__':
    # raw_data = open('tinyUF.txt').read().split("\n")
    # count = int(raw_data[0])
    # data = [map(int, line.split(' ')) for line in raw_data if line != '']

    # res = solution(10, '9-0 2-4 4-5 9-8 6-7 2-6', QuickFindUF)
    # print res == [8, 1, 7, 3, 7, 7, 7, 7, 8, 8], res

    # res = solution(10, '6-8 7-1 0-5 7-3 9-1 1-4 2-9 0-8 9-0', WeightedQuickFindUF)
    # print res == [7, 7, 7, 7, 7, 0, 0, 7, 6, 7], res

    res = solution(10, '4-1 9-5 1-0 8-6 3-0 9-8 2-3 8-4 8-7', WeightedQuickFindUF)
    print ' '.join(map(str, res))

