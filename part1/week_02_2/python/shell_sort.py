#!/usr/local/bin/python
# encoding: utf-8


class ShellSort(object):
    def sort(self, items):
        length = len(items)
        h = 1
        while h < length/3:
            h = 3 * h + 1

        while h >= 1:
            for i in xrange(h, length):
                for j in xrange(i, 0, -h):
                    if int(items[j]) < int(items[j-h]):
                        items[j], items[j-h] = items[j-h], items[j]
                    else:
                        break
            h /= 3
        return items


def solution(data_as_text, alg_class):
    # data = [int(x) for x in data_as_text.split(' ')]
    data = data_as_text
    sort = alg_class()
    return sort.sort(data)

if __name__ == '__main__':
    res = solution([3, 9, 5, 8, 1, '5', 4, 5, 11, 6], ShellSort)
    print res == range(10), res
    # T(N) ~ N^(3/2)
