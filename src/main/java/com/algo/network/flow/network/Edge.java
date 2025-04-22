/**
 * Represents an edge in the flow network
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.network;
/**
 * 
 * The Edge class represents a directed edge in a flow network, including its capacity, flow, and residual edge.
 */
public class Edge {

    private final int targetNode;
    private final int capacity;
    private int flow;
    private Edge residualEdge;


    /**
     * Constructs an edge to the specified target node with the given capacity
     * @param targetNode the node this edge points to
     * @param capacity the capcity og this edge
     */
    public Edge(int targetNode, int capacity) {
        this.targetNode = targetNode;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int getTargetNode() {
        return targetNode;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public Edge getResidualEdge() {
        return residualEdge;
    }

    public void setResidualEdge(Edge residualEdge) {
        this.residualEdge = residualEdge;
    }

    public int getResidualCapacity() {
        return capacity - flow;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "targetNode=" + targetNode +
                ", capacity=" + capacity +
                ", flow=" + flow +
                ", residualEdge=" + residualEdge +
                '}';
    }


}
