#!/usr/local/bin/python
# encoding: utf-8


class Sort(object):
    def sort(self, items):
        for i in xrange(len(items)):
            for j in xrange(i, 0, -1):
                if self.compare(items[j], items[j-1]) < 0:
                    items[j], items[j-1] = items[j-1], items[j]
        return items

    def compare(self, a, b):
        if a > b:
            return 1
        elif a < b:
            return -1
        else:
            return 0


def solution(data_as_text, alg_class):
    data = [int(x) for x in data_as_text.split(' ')]
    sort = alg_class()

    return sort.sort(data)


if __name__ == '__main__':
    res = solution('1 0 3 9 2 8 4 7 5 6', Sort)
    print res == range(10), res
    # T(N) ~ (1/2) * N^2
