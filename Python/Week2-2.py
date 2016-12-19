
def Partition(a, l, r):
    p = a[l]
    i = l + 1
    for j in range(l+1, r+1):
        if a[j] < p:
            t = a[j]
            a[j] = a[i]
            a[i] = t
            i = i + 1
    t = a[l]
    a[l] = a[i-1]
    a[i-1] = t
    return i-1


def QuickSort(a, l, r):
    if l >= r:
        return 0
    t = a[l]
    a[l] = a[r]
    a[r] = t
    index = Partition(a, l, r)
    ll = QuickSort(a, l, index - 1)
    rl = QuickSort(a, index + 1, r)
    return ll + rl + (r-l)
    
    

l = []
f = open("QuickSort.txt")
line = f.readline()
while line:
    l.append(int(line))
    line = f.readline()

f.close()

m = QuickSort(l, 0, len(l) - 1)
print m




    
