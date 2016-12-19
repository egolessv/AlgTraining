

def CountInv(start, end, l):
    if start >= end:
        return ([l[start]],0)
    (left,a) = CountInv(start, (start + end) / 2, l)
    (right,b) = CountInv((start + end) / 2 + 1, end, l)
    (combine,c) = CountSp(left, right)
    return (combine, a+b+c)


def CountSp(left, right):
    i = 0
    j = 0
    count = 0
    combine = []
    for k in range(0, len(left) + len(right)):
        if left[i] < right[j]:
            combine.append(left[i])
            i = i + 1
            if i == len(left):
                combine.extend(right[j:])
                break
        else:
            combine.append(right[j])
            j = j + 1
            count = count + len(left) - i
            if j == len(right):
                combine.extend(left[i:])
                break
            

    
    return (combine,count)

count = 0
l = []
f = open("IntegerArray.txt")
line = f.readline()
while line:
    l.append(int(line))
    line = f.readline()

f.close()

(newlist,n) = CountInv(0, len(l)-1, l)
print n



    
