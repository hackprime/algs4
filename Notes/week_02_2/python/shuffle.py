#!/usr/local/bin/python
# encoding: utf-8
import random

class Shuffle(object):
    def shuffle(self, items):
        length = len(items)
        for i in xrange(length):
            r = int(random.uniform(0, i+1))
            items[r], items[i] = items[i], items[r]
        return items


def solution(data_as_text, alg_class):
    data = [int(x) for x in data_as_text.split(' ')]
    shuffle = alg_class()
    return shuffle.shuffle(data)

if __name__ == '__main__':
    res = solution('0 1 2 3 4 5 6 7 8 9', Shuffle)
    print res == range(10), res
