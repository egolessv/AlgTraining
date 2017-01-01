import sys
import random
import math
import thread

class Node():
    def __init__(self):
        self.Id = -1
        self.To = []
        self.Cost = []
        self.bPick = False


def DijkstraShortestPath(V):
    A = []
    for i in xrange(len(V)):
        A.append(1000000)
    A[1] = 0
    V[1].bPick = True

    while True:
        minDist = 1000000
        minNode = None
        for node in V[1:]:
            if node.bPick == True:
                for i in xrange(len(node.To)):
                    if node.To[i].bPick == False:
                        dist = A[node.Id] + node.Cost[i]
                        if dist < minDist:
                            minDist = dist
                            minNode = node.To[i]
        if minNode is not None:
            minNode.bPick = True
            A[minNode.Id] = minDist
        else:
            break

    for i in (7,37,59,82,99,115,133,165,188,197):
        print A[i]


if __name__ == "__main__":
    nodelist = []
    for i in xrange(201):
        nodelist.append(Node())
                            
    read = open("DijkstraData.txt", 'rb')
    line = read.readline()
    while line:
        nodes = line.split()
        if len(nodes) == 0:
            line = read.readline()
            continue
        sIdx = int(nodes[0])
        nodelist[sIdx].Id = sIdx
        for i in xrange(len(nodes)):
            if i != 0:
                subnodes = nodes[i].split(",")
                dIdx = int(subnodes[0])
                dCost = int(subnodes[1])
                nodelist[sIdx].To.append(nodelist[dIdx])
                nodelist[sIdx].Cost.append(dCost)
        line = read.readline()
    read.close()

    
    DijkstraShortestPath(nodelist)
    
             
            
        

    





