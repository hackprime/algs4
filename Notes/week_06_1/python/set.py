#!/usr/local/bin/python
# encoding: utf-8

class SET(object):
    def __init__(self):
        self.arr = []

    def add(self, key):
        self.arr.append(key)

    def contains(self, key):
        return key in self.arr

    def remove(self, key):
        self.arr.remove(key)

    def __len__(self):
        return len(self.arr)

    def __iter__(self):
        return iter(self.arr)


def test():
    wordlist = "и меня по на сцене бегали ебучие блядские фесты рашкинские".split()

    text = ("блять"
            "вот я вспомнил чето"
            "как у меня бомбануло"
            "когда мы с другом косплеили кастера+рюнноске из фейт зиро"
            "типа вышли такие, поза - постояли - сменили позу"
            "сделали 5 поз под эпичную музыку из внки"
            "и ушли"
            "потом смотрим стоим"
            "какие то уебаны устраивают цирк на сцене "
            "и еще какое то дерьмо "
            "потом орги такие "
            "результаты дефиле "
            "победили дауны которые бегали по сцене "
            "просто пушка нахуй "
            "у меня тогда расщепило жопу на молекулы "
            "и щас я об этом вспомнил "
            "ебучие блядские фесты рашкинские "
            "рот их ебать").split()

    s = SET()
    for word in wordlist:
        s.add(word)

    for word in text:
        if s.contains(word):
            print word

if __name__  == '__main__':
    test()
