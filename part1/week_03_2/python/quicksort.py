#!/usr/local/bin/python
# encoding: utf-8


from copy import copy
from random import shuffle
from collections import OrderedDict


class QuickSort(object):
    ignore_assertions = False

    def sort(self, items):
        shuffle(items)
        self._sort(items, 0, len(items) - 1)
        return items

    def _sort(self, items, start, end):
        if end <= start:
            return
        j = self.partition(items, start, end)
        self._sort(items, start, j-1)
        self._sort(items, j+1, end)

    def partition(self, items, start, end, return_items=False):
        i = start
        j = end
        start = True
        while True:
            if start:
                i += 1
                start = False

            while items[i] <= items[start]:
                i += 1
                if i == end:
                    break

            while items[start] < items[j]:
                j -= 1
                if j == start:
                    break

            if i >= j:
                break
            if items[i] == items[j]:
                i += 1
                j -= 1
            else:
                items[i], items[j] = items[j], items[i]

        items[start], items[j] = items[j], items[start]
        return items if return_items else j

    def partition_solid(self, items, start, end):
        return self.partition(items, start, end, return_items=True)


class InsertionSort(object):
    @classmethod
    def sort(cls, items, start, end):
        for i in xrange(start, end+1):
            for j in xrange(i, 0, -1):
                if items[j] < items[j-1]:
                    items[j], items[j-1] = items[j-1], items[j]
                else:
                    break


class InsertionQuickSort(QuickSort):
    CUTOFF = 10

    def _sort(self, items, start, end):
        if end <= start + self.CUTOFF - 1:
            InsertionSort.sort(items, start, end)
            return
        j = self.partition(items, start, end)
        self._sort(items, start, j-1)
        self._sort(items, j+1, end)


class MedianQuickSort(QuickSort):
    """
    equal size partitions (using median)
    """
    def _sort(self, items, start, end):
        if end <= start:
            return
        m = median(OrderedDict((x, items[x]) for x in [start, start + (end - start) / 2, end]))
        items[start], items[m] = items[m], items[start]

        j = self.partition(items, start, end)
        self._sort(items, start, j-1)
        self._sort(items, j+1, end)


def median(indexed_items):
    sorted_items = OrderedDict(sorted(indexed_items.items(), key=lambda i: i[1]))
    length = len(sorted_items)
    return sorted_items.keys()[length / 2]



class TreeWayPartitionQuickSort(QuickSort):
    def _sort(self, items, start, end):
        if end <= start:
            return

        lt, gt = self.partition(items, start, end)

        self._sort(items, start, lt - 1)
        self._sort(items, gt + 1, end)

    def partition(self, items, start, end, return_items=False):
        lt = start
        i = start
        gt = end
        inplace_item = items[start]

        while i <= gt:
            if items[i] < inplace_item:
                items[lt], items[i] = items[i], items[lt]
                i += 1
                lt += 1
            elif items[i] > inplace_item:
                items[i], items[gt] = items[gt], items[i]
                gt -= 1
            else:
                i += 1

        return items if return_items else (lt, gt)




def solution(data, alg_class):
    alg = alg_class()
    return alg.sort(copy(data))


def partition(data, alg_class):
    alg = alg_class()
    alg.partition_solid(data, 0, len(data)-1)
    return data


if __name__ == '__main__':
    # data = '8 1 0 7 6 2 9 4 3 5'.split()
    # expected_result = '0 1 2 3 4 5 6 7 8 9'.split()

    # res = solution(data, QuickSort)
    # print res == expected_result, res

    # res = solution(data, InsertionQuickSort)
    # print res == expected_result, res

    # res = solution(data, MedianQuickSort)
    # print res == expected_result, res

    # three_way_data = [6, 4, 9, 1, 6, 6, 8, 4, 0, 6, 6, 1, 6, 2, 6]
    # res = solution(three_way_data, TreeWayPartitionQuickSort)
    # print res == [0, 1, 1, 2, 4, 4, 6, 6, 6, 6, 6, 6, 6, 8, 9], res

    res = partition('41 25 87 75 16 24 93 88 19 60 10 96'.split(), QuickSort)
    print ' '.join(res)

    res = partition('A B A B B B B B A B A A'.split(), QuickSort)
    print ' '.join(res)

    # res = partition('49 97 49 36 49 29 23 25 41 10'.split(), TreeWayPartitionQuickSort)
    # print ' '.join(res)
