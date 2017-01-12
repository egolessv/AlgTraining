
def HashSlot(value, prime):
    return abs(value) % prime

if __name__ == "__main__":
    l = []
    table = []
    KeyPrime = 524341
    for i in xrange(KeyPrime):
        table.append([])
    
    kFile = open('2sum.txt', 'rb')
    while True:
        line = kFile.readline()
        if not line:
            break
        value = int(line)
        l.append(value)
        slot = HashSlot(value, KeyPrime)
        table[slot].append(value)

    count = 0
    for t in xrange(-10000, 10001):
        if t % 10 == 0:
            print t
        bFound = False
        for x in l:
            y = t - x
            if x == y:
                continue
            slot = HashSlot(y, KeyPrime)
            for k in table[slot]:
                if k == y:
                    bFound = True
                    break
            if bFound:
                count = count + 1
                break
    print count

    tFile = open('reslut.txt', 'wb')
    tFile.write(str(count))
    tFile.close()
            
        
