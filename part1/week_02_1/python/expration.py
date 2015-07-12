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


def dijkstra_alg(expression_as_text, alg_class):
    """
    Dijkstra's two-stacks algorythm
    """
    value_stack = alg_class()
    operator_stack = alg_class()

    for char in expression_as_text.split():
        if char.isdigit():
            value_stack.push(char)
        elif char in '+-/*':
            operator_stack.push(char)
        elif char == ')':
            value = eval('%(operand)s%(sign)s%(operator)s' % {'operator': value_stack.pop(),
                                                              'sign': operator_stack.pop(),
                                                              'operand': value_stack.pop()})
            value_stack.push(str(value))
    return value_stack.pop()


if __name__ == '__main__':
    res = dijkstra_alg('( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )', LinkedListStack)
    print res == '101', res

    res = dijkstra_alg('( ( 9 - 4 ) * ( 4 / 2 ) ) + 6 )', LinkedListStack)
    print res == '16', res

    res = dijkstra_alg('( ( 34 - 4 ) * ( 2 / 2 ) ) - 40 )', LinkedListStack)
    print res == '-10', res
