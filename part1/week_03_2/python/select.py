#!/usr/local/bin/python
# encoding: utf-8


from copy import copy
from random import shuffle
from collections import OrderedDict


class Select(object):
    def select(self, items, k):
        raise NotImplementedError


class QuickSelect(object):
    def select(self, items, k):
        shuffle(items)
        start = 0
        end = len(items) - 1
        while end > start:
            j = self.partition(items, start, end)
            if j < k:
                start = j + 1
            elif j > k:
                end = j + 1
            else:
                return items[k]
        return items[k]

    def partition(self, items, start, end):
        i = start
        j = end
        while True:
            while items[i] < items[start]:
                i += 1
                if i == end:
                    break

            while items[start] < items[j]:
                j -= 1
                if j == start:
                    break

            if i >= j:
                break
            items[i], items[j] = items[j], items[i]

        items[start], items[j] = items[j], items[start]
        return j


def median(indexed_items):
    sorted_items = OrderedDict(sorted(indexed_items.items(), key=lambda i: i[1]))
    length = len(sorted_items)
    return sorted_items.keys()[length / 2]


def solution(data, k, alg_class):
    alg = alg_class()
    return alg.select(map(int, copy(data)), k)


if __name__ == '__main__':
    data = '0 1 2 3 22 4 5 40 6 7 8 9 21'.split()

    res = solution(data, 10, QuickSelect)
    print res == 21, res
