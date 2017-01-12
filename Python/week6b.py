
class MinHeap():
    def __init__(self):
        self.arr = []
        self.arr.append(0)

    def GetSize(self):
        return len(self.arr) - 1

    def Push(self, value):
        self.arr.append(value)
        last = len(self.arr) - 1
        while last > 1:
            if self.arr[last] < self.arr[last/2]:
                self.SwapByIdx(last, last/2)
                last =  last / 2
            else:
                break

    def Top(self):
        return self.arr[1]

    def Pop(self):
        last = len(self.arr) - 1
        self.SwapByIdx(1, last)
        ret = self.arr.pop()
        idx = 1
        last = len(self.arr) - 1
        while True:
            left = idx * 2
            right = idx * 2 + 1
            if left <= last and right <= last:
                if self.arr[idx] < self.arr[left] and self.arr[idx] < self.arr[right]:
                    break
                else:
                    if self.arr[left] < self.arr[right]:
                        self.SwapByIdx(idx, left)
                        idx = left
                    else:
                        self.SwapByIdx(idx, right)
                        idx = right
            elif left <= last:
                if self.arr[idx] < self.arr[left]:
                    break
                else:
                    self.SwapByIdx(idx, left)
                    idx = left
            else:
                break  
            
        return ret
                
        
    def SwapByIdx(self, a, b):
        temp = self.arr[a]
        self.arr[a] = self.arr[b]
        self.arr[b] = temp

    def PrintParents(self, value):
        index = -1
        for i in xrange(1, self.GetSize()):
            if self.arr[i] == value:
                index = i
                break
        
        if index != -1:
            print self.arr[index]
            while index > 1:
                index = index / 2
                print self.arr[index]

                
class MaxHeap():
    def __init__(self):
        self.arr = []
        self.arr.append(0)

    def GetSize(self):
        return len(self.arr) - 1

    def Push(self, value):
        self.arr.append(value)
        last = len(self.arr) - 1
        while last > 1:
            if self.arr[last] > self.arr[last/2]:
                self.SwapByIdx(last, last/2)
                last =  last / 2
            else:
                break

    def Top(self):
        return self.arr[1]

    def Pop(self):
        last = len(self.arr) - 1
        self.SwapByIdx(1, last)
        ret = self.arr.pop()
        idx = 1
        last = len(self.arr) - 1
        while True:
            left = idx * 2
            right = idx * 2 + 1
            if left <= last and right <= last:
                if self.arr[idx] > self.arr[left] and self.arr[idx] > self.arr[right]:
                    break
                else:
                    if self.arr[left] > self.arr[right]:
                        self.SwapByIdx(idx, left)
                        idx = left
                    else:
                        self.SwapByIdx(idx, right)
                        idx = right
            elif left <= last:
                if self.arr[idx] > self.arr[left]:
                    break
                else:
                    self.SwapByIdx(idx, left)
                    idx = left
            else:
                break  
            
        return ret
                
        
    def SwapByIdx(self, a, b):
        temp = self.arr[a]
        self.arr[a] = self.arr[b]
        self.arr[b] = temp

    def PrintParents(self, value):
        index = -1
        for i in xrange(1, self.GetSize()):
            if self.arr[i] == value:
                index = i
                break
        
        if index != -1:
            print self.arr[index]
            while index > 1:
                index = index / 2
                print self.arr[index]


def InsertValue(maxHeap, minHeap, value):
    ## this case we should add value to min heap
    if maxHeap.GetSize() > minHeap.GetSize():
        if value >= maxHeap.Top():
            minHeap.Push(value)
        else:
            temp = maxHeap.Pop()
            minHeap.Push(temp)
            maxHeap.Push(value)
    else:
        if maxHeap.GetSize() == 0 or value <= minHeap.Top():
            maxHeap.Push(value)
        else:
            temp = minHeap.Pop()
            maxHeap.Push(temp)
            minHeap.Push(value)

    return maxHeap.Top()



if __name__ == "__main__":
    kMaxHeap = MaxHeap()
    kMinHeap = MinHeap()
    TotalMedian = 0
    kFile = open('Median.txt', 'rb')
    while True:
        line = kFile.readline()
        if not line:
            break
        value = int(line)
        TotalMedian = TotalMedian + InsertValue(kMaxHeap, kMinHeap, value)

    result = TotalMedian % 10000
    print result, TotalMedian
    

    
        
