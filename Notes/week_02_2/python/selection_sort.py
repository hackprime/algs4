#!/usr/local/bin/python
# encoding: utf-8


class SelectionSort(object):
    def sort(self, items):
        length = len(items)
        for i in xrange(length):
            min_item_index = i
            for j in xrange(i, length):
                if items[j] < items[min_item_index]:
                    min_item_index = j
            items[i], items[min_item_index] = items[min_item_index], items[i]
            print items
        return items


def solution(data_as_text, alg_class):
    data = [x for x in data_as_text.split(' ')]
    sort = alg_class()

    print data
    return sort.sort(data)


if __name__ == '__main__':
    # res = solution('1 0 3 9 2 8 4 7 5 6', SelectionSort)
    # print res == range(10), res
    # # T(N) ~ (1/2) * N^2

    res = solution('sand buff corn navy pine blue rust silk herb bone jade gray flax puce iris lust', SelectionSort)
    # print res == range(10), res
