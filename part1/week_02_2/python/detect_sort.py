#!/usr/local/bin/python
# encoding: utf-8

from copy import copy


class BaseSort(object):
    intermediate_values = []
    store_intermediate_values = False

    def has_intermediate_value(self, row):
        return row in self.intermediate_values


class InsertionSort(BaseSort):
    def sort(self, items):
        print 'InsertionSort'
        length = len(items)
        for i in xrange(length):
            for j in xrange(i, 0, -1):
                if items[j] < items[j-1]:
                    items[j], items[j-1] = items[j-1], items[j]
                    if self.store_intermediate_values:
                        print items
                        self.intermediate_values.append(copy(items))
                else:
                    break

        return items


class SelectionSort(BaseSort):
    def sort(self, items):
        print 'SelectionSort'
        length = len(items)
        for i in xrange(length):
            min_item_index = i
            for j in xrange(i, length):
                if items[j] < items[min_item_index]:
                    min_item_index = j
            items[i], items[min_item_index] = items[min_item_index], items[i]
            if self.store_intermediate_values:
                print items
                self.intermediate_values.append(copy(items))
        return items


class ShellSort(BaseSort):
    def sort(self, items):
        print 'ShellSort'
        length = len(items)
        h = 1
        while h < length/3:
            h = 3 * h + 1

        while h >= 1:
            for i in xrange(h, length):
                for j in xrange(i, 0, -h):
                    if items[j] < items[j-h]:
                        items[j], items[j-h] = items[j-h], items[j]
                        if self.store_intermediate_values:
                            print items
                            self.intermediate_values.append(copy(items))
                    else:
                        break
            h /= 3
        return items


SORTS = {
    1: InsertionSort,
    2: SelectionSort,
    3: ShellSort,
}


def solution(data):
    algs_sequence = []
    for i in range(1, len(data)):
        got_solution = False

        for alg_num, Alg in SORTS.items():
            alg = Alg()
            alg.store_intermediate_values = True
            alg.sort(copy(data[i-1]))
            got_solution = alg.has_intermediate_value(data[i])

            if got_solution:
                algs_sequence.append(alg_num)
                break

        if not got_solution:
            algs_sequence.append(None)
            # raise Exception('Something goes wrong! Intermediate solution [%s]' % ', '.join(map(str, algs_sequence)))

    return algs_sequence


if __name__ == '__main__':
    data = zip(('corn', 'corn', 'corn', 'corn', 'cafe', 'cafe', 'corn', 'cafe'),
               ('pear', 'ecru', 'ecru', 'gray', 'corn', 'corn', 'pear', 'corn'),
               ('ecru', 'fawn', 'fawn', 'cafe', 'ecru', 'ecru', 'cafe', 'ecru'),
               ('fawn', 'onyx', 'gray', 'ecru', 'fawn', 'fawn', 'fawn', 'fawn'),
               ('onyx', 'pear', 'kobi', 'mist', 'gray', 'gray', 'onyx', 'gray'),
               ('gray', 'gray', 'lava', 'pear', 'kobi', 'kobi', 'gray', 'kobi'),
               ('kobi', 'kobi', 'onyx', 'kobi', 'lava', 'lava', 'kobi', 'lava'),
               ('lava', 'lava', 'pear', 'fawn', 'leaf', 'leaf', 'lava', 'leaf'),
               ('mist', 'mist', 'mist', 'onyx', 'mist', 'mint', 'mist', 'mint'),
               ('wine', 'wine', 'wine', 'sand', 'wine', 'wine', 'wine', 'mist'),
               ('mint', 'mint', 'mint', 'mint', 'mint', 'mist', 'mint', 'onyx'),
               ('leaf', 'leaf', 'leaf', 'lava', 'onyx', 'onyx', 'leaf', 'pear'),
               ('silk', 'silk', 'silk', 'silk', 'silk', 'silk', 'silk', 'ruby'),
               ('sand', 'sand', 'sand', 'wine', 'sand', 'sand', 'sand', 'sand'),
               ('ruby', 'ruby', 'ruby', 'ruby', 'ruby', 'ruby', 'ruby', 'silk'),
               ('cafe', 'cafe', 'cafe', 'leaf', 'pear', 'pear', 'ecru', 'wine'))
    data = map(list, data)
    res = solution(data)
    print res == [1, 1, 3, 2, 2, 3], res
    # # T(N) ~ (1/2) * N^2
