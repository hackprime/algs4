#!/usr/local/bin/python
# encoding: utf-8


class BinarySearch(object):
    def __init__(self, arr):
        self.arr = arr
        self.n = len(arr)

    def find(self, number):
        left = 0
        right = self.n - 1
        while left <= right:
            middle = left + (right - left) / 2
            if number > self.arr[middle]:
                left = middle + 1
            elif number < self.arr[middle]:
                right = middle - 1
            else:
                return middle
        return None


def solution(arr_text, number, cls):
    arr = [int(x) for x in arr_text.split(' ')]
    bs = cls(arr)
    return bs.find(number)


if __name__ == '__main__':
    arr_text = '6 13 14 25 33 43 51 53 64 72 84 93 95 96 97'
    res = solution(arr_text, 33, BinarySearch)
    print res is not None, res

    res = solution(arr_text, 34, BinarySearch)
    print res is None, res
