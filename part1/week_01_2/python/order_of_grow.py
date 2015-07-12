#!/usr/local/bin/python
# encoding: utf-8
from math import log


class OrderOfGrow(object):
    @classmethod
    def estimate_b(cls, arr):
        log_arr = []
        for _, n in input_data:
            try:
                log_arr.append(log(n, 2))
            except ValueError:
                pass
        log_ratio = [log_arr[i+1] - log_arr[i] for i in range(len(log_arr) - 1)]
        print log_ratio
        b = reduce(lambda x, y: x + y, log_ratio) / len(log_ratio)
        return b
        # T(N) = a * N^b
        # lg(T(N)) = b * lg(N) + c
        # c = 2^a
        # lg(T(N)) = b * lg(N) + 2^a


def solution(arr, cls):
    return cls.estimate_b(arr)


if __name__ == '__main__':
    # input_data = [
    #     (250, 0.0),
    #     (500, 0.0),
    #     (1000, 0.1),
    #     (2000, 0.8),
    #     (4000, 6.4),
    #     (8000, 51.1),
    #     # (16000000, None),
    # ]
    # res = solution(input_data, OrderOfGrow)
    # print res


    # input_data = [
    #     (512,    0.000),
    #     (1024,   0.002),
    #     (2048,   0.010),
    #     (4096,   0.053),
    #     (8192,   0.284),
    #     (16384,  1.532),
    #     (32768,  8.189),
    #     (65536,  43.635),
    #     (131072, 233.218),
    #     (262144, 1245.288)
    # ]
    # res = solution(input_data, OrderOfGrow)
    # print res

    # input_data = [
    #     (512, 0.000),
    #     (1024, 0.002),
    #     (2048, 0.009),
    #     (4096, 0.038),
    #     (8192, 0.171),
    #     (16384, 0.776),
    #     (32768, 3.549),
    #     (65536, 16.149),
    #     (131072, 73.158),
    #     (262144, 332.508),
    #     (524288, 1507.424),
    # ]
    # res = solution(input_data, OrderOfGrow)
    # print res

    input_data = [
        (2048, 0.000),
        (4096, 0.001),
        (8192, 0.002),
        (16384, 0.007),
        (32768, 0.021),
        (65536, 0.064),
        (131072, 0.190),
        (262144, 0.571),
        (524288, 1.703),
        (1048576, 5.117),
        (2097152, 15.313),
        (4194304, 45.907),
        (8388608, 137.575),
        (16777216, 412.330),
        (33554432, 1235.703),
    ]
    res = solution(input_data, OrderOfGrow)
    print res
