import math
import random

def generateN_by_1(n, limit, fix):
    l = set()
    for x in xrange(n):
        l.add(random.randrange(limit))
    filename = str(len(l)) + "by1.txt"
    f = open(filename, 'wb')
    f.write(str(len(l)) + "\n")
    for s in l:
        f.write(str(s) + " " + str(fix) + "\n")
    f.close()

def test():
    f = open("test1024.txt", 'wb')
    f.write("1024\n")
    for y in xrange(4):
        for x in xrange(256):
            f.write(str(x*100) + " " + str(y*1000) + "\n")
    f.close()


if __name__ == "__main__":
#    generateN_by_1(100000, 200000, 5000)
    test()
