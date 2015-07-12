#!/usr/local/bin/python
# encoding: utf-8


from copy import copy

class PriorityQueue(object):
    def insert(self, item):
        raise NotImplementedError

    def is_empty(self, item):
        raise NotImplementedError

    def delete(self, item):
        raise NotImplementedError


class UnorderedMaxPriorityQueue(PriorityQueue):
    def __init__(self, limit):
        self.pq = []
        self.limit = limit

    def insert(self, item):
        self.pq.append(item)

    def delete_max(self):
        max_index = 0
        for i, item in enumerate(self.pq):
            if item.value > self.pq[max_index].value:
                max_index = i
        del self.pq[max_index]

    def size(self):
        return len(self.pq)

    def is_empty(self, item):
        return self.size() == 0


class Item(object):
    def __init__(self, name, date, value):
        self.name = name
        self.date = date
        self.value = value


def bottom_three(data, alg_class, limit):
    pq = alg_class(limit)
    for name, date, value in data:
        item = Item(name, date, value)
        pq.insert(item)
        # print [item.value for item in pq.pq]
        if pq.size() > pq.limit:
            pq.delete_max()
    return [item.value for item in pq.pq]

if __name__ == '__main__':
    data = [('john', '2010.09.09', 145.94),
            ('alex', '2011.01.22', 56.24),
            ('itan', '2010.06.24', 736.03),
            ('max', '2010.09.10', 498.07),
            ('ian', '2010.11.17', 438.09),
            ('anthony', '2010.12.28', 384.55)]
    res = bottom_three(data, UnorderedMaxPriorityQueue, 3)
    print res == [145.94, 56.24, 384.55], res
