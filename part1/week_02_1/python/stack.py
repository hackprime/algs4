#!/usr/local/bin/python
# encoding: utf-8


class StackItem(object):
    data = None
    next = None

    def __init__(self, s):
        self.data = s


class StackOfStrings(object):
    # def __init__(self, numbers):
    #     self.numbers = numbers
    #     self.n = len(numbers)
    size = None

    def push(self, item):
        raise NotImplementedError

    def pop(self):
        raise NotImplementedError

    def is_empty(self):
        raise NotImplementedError


class LinkedListStack(StackOfStrings):
    first = None

    def push(self, item):
        if isinstance(item, basestring):
            item = StackItem(item)
        oldfirst = self.first
        item.next = oldfirst
        self.first = item

    def pop(self):
        item = self.first
        self.first = self.first.next
        return item.data

    def is_empty(self):
        return self.first is None


class AtrrayStack(StackOfStrings):
    """
    okay, let's play in Java and try to avoid standard methods of list.
    """
    n = 0

    def __init__(self, capacity):
        self.capacity = capacity
        self.array = [None] * capacity

    def push(self, item):
        self.array[self.n] = item
        self.n += 1

    def pop(self):
        self.n -= 1
        item = self.array[self.n]
        self.array[self.n] = None
        return item

    def is_empty(self):
        return self.n == 0


class ResizableAtrrayStack(AtrrayStack):
    def __init__(self):
        self.array = [None]

    def push(self, item):
        array_len = len(self.array)
        if array_len == self.n:
            self.resize(2 * array_len)
        self.array[self.n] = item
        self.n += 1
        print self.array

    def pop(self):
        self.n -= 1
        item = self.array[self.n]
        self.array[self.n] = None
        if self.n == len(self.array)/4:
            self.resize(self.n)
        return item

    def is_empty(self):
        return self.n == 0

    def resize(self, size):
        array_len = len(self.array)
        if size > array_len:
            self.array += [None] * (size / 2)
        else:
            self.array = self.array[:(size)]


def solution(data_as_string, alg_class, capacity=None):
    data = data_as_string.split(' ')
    stack = alg_class(capacity) if capacity else alg_class()
    popped_data = []
    for string in data:
        if string == '-':
            popped_data.append(stack.pop())
        else:
            stack.push(string)
    return popped_data


if __name__ == '__main__':
    res = solution('to be or not to - be - - that - - - is', LinkedListStack)
    print res == 'to be not that or be'.split(), res

    res = solution('to be or not to - be - - that - - - is', AtrrayStack, 10)
    print res == 'to be not that or be'.split(), res

    res = solution('to be or not to - be - - that - - - is', ResizableAtrrayStack)
    print res == 'to be not that or be'.split(), res
