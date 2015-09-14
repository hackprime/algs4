#!/usr/local/bin/python
# encoding: utf-8


from copy import copy


class MergeSort(object):
    ignore_assertions = False

    def sort(self, items):
        length = len(items)
        start = 0
        end = length - 1
        aux = [None] * length
        self._sort(items, aux, start, end)
        return items

    def _sort(self, items, aux_items, start, end):
        if end <= start:
            return

        middle = start + (end - start) / 2
        self._sort(items, aux_items, start, middle)
        self._sort(items, aux_items, middle+1, end)
        self.merge(items, aux_items, start, middle, end)
        print ' '.join(map(str, items))

    def merge(self, items, aux_items, start, middle, end):
        assert self.ignore_assertions or self.is_sorted(items, start, middle)
        assert self.ignore_assertions or self.is_sorted(items, middle + 1, end)

        aux_items = copy(items)

        i = start
        j = middle + 1
        for k in xrange(start, end + 1):
            if i > middle:
                items[k] = aux_items[j]
                j += 1
            elif j > end:
                items[k] = aux_items[i]
                i += 1
            elif aux_items[j] < aux_items[i]:
                items[k] = aux_items[j]
                j += 1
            else:
                items[k] = aux_items[i]
                i += 1

        assert self.ignore_assertions or self.is_sorted(items, start, end)

    def is_sorted(self, items, i, j):
        for x in xrange(i, j):
            if items[x] > items[x+1]:
                return False
        return True


class InsertionSort(object):
    @classmethod
    def sort(cls, items, start, end):
        for i in xrange(start, end+1):
            for j in xrange(i, 0, -1):
                if items[j] < items[j-1]:
                    items[j], items[j-1] = items[j-1], items[j]
                else:
                    break


class InsertionMergeSort(MergeSort):
    # improvement 1: use insertion sort for subarrays with length less than 7
    CUTOFF = 7

    def _sort(self, items, aux_items, start, end):
        if end <= start + self.CUTOFF - 1:
            InsertionSort.sort(items, start, end)
            return

        middle = start + (end - start) / 2
        self._sort(items, aux_items, start, middle)
        self._sort(items, aux_items, middle+1, end)
        # improvement 2: stop if array already sorted
        if items[middle] < items[middle + 1]:
            return
        self.merge(items, aux_items, start, middle, end)


class BottomUpMergeSort(MergeSort):
    ignore_assertions = True

    def sort(self, items):
        length = len(items)
        aux_items = [None] * length
        size = 1
        while size < length:
            for j in xrange(0, length - size, 2 * size):
                start = j
                middle = start + size - 1
                end = min([start + 2 * size - 1, length - 1])
                self.merge(items, aux_items, start, middle, end)
                print ' '.join(items)
            size += size
        return items


def solution(data, alg_class):
    alg = alg_class()
    return alg.sort(copy(data))


if __name__ == '__main__':
    # data = '8 1 0 7 6 2 9 4 3 5'.split()
    # expected_result = '0 1 2 3 4 5 6 7 8 9'.split()

    # res = solution(data, MergeSort)
    # print res == expected_result, res

    # res = solution(data, InsertionMergeSort)
    # print res == expected_result, res

    # res = solution(data, BottomUpMergeSort)
    # print res == expected_result, res

    res = solution('98 42 70 95 76 99 62 37 72 34 66 32'.split(), MergeSort)
    print ' '.join(map(str, res))

    res = solution('71 78 11 56 99 93 39 59 17 53'.split(), BottomUpMergeSort)
    print ' '.join(map(str, res))

