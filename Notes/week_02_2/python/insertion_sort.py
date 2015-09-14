#!/usr/local/bin/python
# encoding: utf-8


class InsertionSort(object):
    def sort(self, items):
        length = len(items)
        for i in xrange(length):
            for j in xrange(i, 0, -1):
                if items[j] < items[j-1]:
                    items[j], items[j-1] = items[j-1], items[j]
                else:
                    break
        return items


def solution(data_as_text, alg_class):
    data = [int(x) for x in data_as_text.split(' ')]
    sort = alg_class()
    return sort.sort(data)


if __name__ == '__main__':
    res = solution('1 0 3 9 2 8 4 7 5 6', InsertionSort)
    print res == range(10), res
    # T(N) ~ 0.25 * N^2
