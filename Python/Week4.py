import sys
import random
import math
import thread

class Node():
    def __init__(self):
        self.From = []
        self.To = []
        self.bMark = False
        self.Leader = None

def DFS(G, node, depth):
    global leader
    global count
    if node.bMark == True:
        return
    node.bMark = True
    node.Leader = leader
    count = count + 1
    depth = depth + 1
    if depth % 5000 == 0:
        print depth
    for i in node.To:
        DFS(G, G[i], depth)
        

def RevDFS(G, node, depth):
    global F
    if node.bMark == True:
        return
    node.bMark = True
    depth = depth + 1
    if depth % 5000 == 0:
        print depth
    for i in node.From:
        RevDFS(G, G[i], depth)
    F.append(node)


def ResetG(G):
    for node in G:
        node.bMark = False

def ThreadGraph(G):
    global F
    global leader
    global count
    for i in xrange(875714, 0, -1):
        if G[i].bMark == False:
            RevDFS(G, G[i], 0)
    
    print "finish count finish times"

    ResetG(G)
    
    sccCountList = []
    
    for i in xrange(875714, 0, -1):
        if F[i].bMark == False:
            leader = F[i]
            count = 0
            DFS(G, F[i], 0)
            sccCountList.append(count)

    sccCountList.sort(reverse=True)
    print sccCountList[:5]
    

if __name__ == "__main__":
    sys.setrecursionlimit(200000)
    nodelist = []
    F = [Node()]
    count = 0
    leader = None
    for i in xrange(875715):
        nodelist.append(Node())
                            
    read = open("SCC.txt", 'rb')
    line = read.readline()
    while line:
        nodes = line.split()
        if len(nodes) == 0:
            line = read.readline()
            continue
        firstIdx = int(nodes[0])
        secondIdx = int(nodes[1])
        if firstIdx != secondIdx:
            nodelist[firstIdx].To.append(secondIdx)
            nodelist[secondIdx].From.append(firstIdx)
        line = read.readline()
    read.close()

    thread.stack_size(1048576*128)
    thread.start_new_thread(ThreadGraph, (nodelist,))
    
             
            
        

    





