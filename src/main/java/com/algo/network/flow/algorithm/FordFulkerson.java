/**
 * Implements the Ford-Fulkerson method Edmonds-Karp Algorithm for maximum flow
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.algorithm;


import com.algo.network.flow.network.Edge;
import com.algo.network.flow.network.FlowNetwork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The FordFulkerson class provides a method to compute the maximum flow in a  flow network using the Edmonds-Karp algorithm
 */
public class FordFulkerson {

    /**
     * Computes the maximum flow from source to sink in the given network.
     *
     * @param network the flow network
     * @return the value of the maximum flow
     */
    public static int computeMaximumFlow(FlowNetwork network){
        int maxFlow = 0;
        List<Integer> augmentingPath;

        while((augmentingPath = findAugmentingPath(network))!=null){
            //have to find bottleneck
            //update the redisual path
            //add bottleneck to the maxflow
            //print augmenting path
        }
        return maxFlow;
    }

    /**
     * Finds an augmenting path using BFS and returns the path as a list of node indices
     *
     * @param network the flow network
     * @return the augmenting path or null of none exists
     */
    private static List<Integer> findAugmentingPath(FlowNetwork network) {
        int[] parent = new int[network.getNumberOfNodes()];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(network.getSourceNode());
        parent[network.getSourceNode()] = -2;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            for(Edge edge: network.getAdjacencyList().get(currentNode)){
                int nextNode = edge.getTargetNode();
                if (parent[nextNode] == -1 && edge.getResidualCapacity() > 0) {
                    parent[nextNode] = currentNode;
                    if(nextNode == network.getSinkNode()){
                        //have to return a method to build the augmented path from the parent array.
                    }
                    queue.add(nextNode);
                }
            }
        }
        return null;
    }


}
