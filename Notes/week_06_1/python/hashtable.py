#!/usr/local/bin/python
# -*- coding: utf-8 -*-


class Node(object):
    key = None
    value = None
    next = None

    def __init__(self, key, value, nxt):
        self.key, self.value, self.next = key, value, nxt


class SeparateChainingHT(object):
    st = None
    key_to_hash = None
    M = 10

    def __init__(self, key_to_hash):
        self.st = [None] * self.M
        self.key_to_hash = {k: int(v) for k, v in key_to_hash}

    def hash(self, key):
        return self.key_to_hash.get(key)

    def get(self, key):
        i = self.hash(key)
        x = self.st[i]
        while x is not None:
            if key == x.key:
                return x.value
            x = x.next
        return None

    def put(self, key, value):
        i = self.hash(key)
        x = self.st[i]
        while x is not None:
            if key == x.key:
                x.value = value
                return
            x = x.next
        self.st[i] = Node(key, value, self.st[i])


class LinearProbingHT(object):
    vals = None
    keys = None
    key_to_hash = None
    M = 10

    def __init__(self, key_to_hash):
        self.st = [None] * self.M
        self.keys = [None] * self.M
        self.vals = [None] * self.M
        self.key_to_hash = {k: int(v) for k, v in key_to_hash}

    def hash(self, key):
        return self.key_to_hash.get(key)

    def put(self, key, val):
        i = self.hash(key)
        while self.keys[i] is not None:
            if key == self.keys[i]:
                break
            i = (i + 1) % self.M
        self.keys[i] = key
        self.vals[i] = val


def prepare_input(strings):
    import re
    return map(lambda s: re.sub(r'\s+', ' ', s).split(' '), strings.strip(" \n").split("\n"))


def task_1(input_string, target_key, target_hash=None):
    input_data = prepare_input(input_string)
    ht = SeparateChainingHT(input_data)
    target_hash = target_hash or ht.key_to_hash.get(target_key)
    for k, h in input_data:
        ht.put(k, None)
    i = ht.key_to_hash.get(target_key, target_hash)
    found_keys = []
    x = ht.st[i]
    while x is not None:
        found_keys.append(x.key)
        x = x.next
    return ' '.join(found_keys)


def task_2(input_string):
    input_data = prepare_input(input_string)
    ht = LinearProbingHT(input_data)
    for k, h in input_data:
        ht.put(k, None)
    return ' '.join(ht.keys)


if __name__ == '__main__':
    # task 1
    ipt = """
H    0
E    0
F    1
Q    0
V    2
R    1
Y    2
S    2
L    1
A    2
K    0
W    0
    """
    print task_1(ipt, 'D', 2)

    # task 2
    print task_2("""
C    7
N    8
K    5
R    2
D    8
E    9
I    3
S    3
U    5
J    4
    """)


    # # task 3
    # print task_3(
    # )
