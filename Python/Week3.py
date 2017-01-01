
import random
import math

def RandomizeContraction(nodes):
    while len(nodes) > 2:
        keys = nodes.keys()
        nIndex = random.randrange(len(keys))
        head = keys[nIndex]
        edges = nodes.get(head)
        eIndex = random.randrange(len(edges))
        tail = edges[eIndex]
        edgesToMerge = nodes.get(tail)
        ##print head, tail
        count = 0
        for e in edgesToMerge:               
            if e == head:
                count = count + 1
            else:
                edgesToUpdate = nodes.get(e)
                for i in range(len(edgesToUpdate)):
                    if edgesToUpdate[i] == tail:
                        edgesToUpdate[i] = head
                        
        while count > 0:
            edgesToMerge.remove(head)
            count = count - 1

        count = 0
        for e in edges:
            if e == tail:
                count = count + 1
        while count > 0:
            edges.remove(tail)
            count = count - 1

        edges.extend(edgesToMerge)
        nodes.pop(tail)
        
    for key in nodes.keys():
        return len(nodes[key])
    
        
def CopyOfNodes(nodes):
    nodesCopy = {}
    for key in nodes.keys():
        item = nodes.get(key)
        itemCopy = []
        for it in item:
            itemCopy.append(it)
        nodesCopy[key] = itemCopy
    return nodesCopy
    
        



if __name__ == "__main__":
    AllNodes = {}
    read = open("kargetMinCut.txt", 'rb')
    line = read.readline()
    while line:
        nodes = line.split()
        nodesInt = []
        key = int(nodes[0])
        for node in nodes[1:]:
            nodesInt.append(int(node))
        AllNodes[key] = nodesInt
        line = read.readline()
    read.close()

    n = len(AllNodes)
##    print n
##    AllNodesList = []
##    count = 0
##    for i in range(n*n):
##        AllNodesList.append(CopyOfNodes(AllNodes))
##        count = count + 1
##        if count % 50 == 0:
##            print count
##    print "data is prepared"

    
    distMin = n*n
    for i in range(n*n):
        dist = RandomizeContraction(CopyOfNodes(AllNodes))
        print dist, distMin
        if dist < distMin:
            distMin = dist
    print distMin



