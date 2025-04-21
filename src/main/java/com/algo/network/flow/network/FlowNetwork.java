/**
 * Represents a flow network using adjacency lists.
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.network;

import java.util.ArrayList;
import java.util.List;

/**
 * The Flow Network class represnts a directed graph for network flow problems
 */
public class FlowNetwork {
    private final int numberOfNodes;
    private final int sourceNode;
    private final int sinkNode;
    private final List<List<Edge>> adjacencyList;


    /**
     * Constructs a flow network with the specified number of nodes, source, and sink.
     *
     * @param numberOfNodes the total number of nodes
     * @param sourceNode the source node index
     * @param sinkNode the sink node index
     */
    public FlowNetwork(int numberOfNodes, int sourceNode, int sinkNode) {
        this.numberOfNodes = numberOfNodes;
        this.sourceNode = sourceNode;
        this.sinkNode = sinkNode;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getSourceNode() {
        return sourceNode;
    }

    public int getSinkNode() {
        return sinkNode;
    }

    public List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public String toString() {
        return "FlowNetwork{" +
                "numberOfNodes=" + numberOfNodes +
                ", sourceNode=" + sourceNode +
                ", sinkNode=" + sinkNode +
                ", adjacencyList=" + adjacencyList +
                '}';
    }
}
