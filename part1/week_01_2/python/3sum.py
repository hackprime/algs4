#!/usr/local/bin/python
# encoding: utf-8

class TS(object):
    def __init__(self, numbers):
        self.numbers = numbers
        self.n = len(numbers)

    def zerosums(self):
        raise NotImplementedError


class ThreeSumTS(TS):
    def zerosums(self):
        count = 0
        for i in xrange(self.n):
            for j in xrange(i+1, self.n):
                for k in xrange(j+1, self.n):
                    if self.numbers[i] + self.numbers[j] + self.numbers[k] == 0:
                        count += 1
        return count


def solution(n, data_as_string, alg_class):
    data = [int(x) for x in data_as_string.split(' ')]
    ts = alg_class(data)
    return ts.zerosums()


if __name__ == '__main__':
    res = solution(8, '30 -40 -20 -10 40 0 10 5', ThreeSumTS)
    print res == 4, res
