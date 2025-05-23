/**
 * Implements the Ford-Fulkerson method Edmonds-Karp Algorithm for maximum flow
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.algorithm;

import com.algo.network.flow.network.Edge;
import com.algo.network.flow.network.FlowNetwork;

import java.util.*;

/**
 * The FordFulkerson class provides a method to compute the maximum flow in a flow network using the Edmonds-Karp algorithm
 */
public class FordFulkerson {

    /**
     * Computes the maximum flow from source to sink in the given network.
     *
     * @param network the flow network
     * @return the value of the maximum flow
     */
    public static int computeMaximumFlow(FlowNetwork network) {
        int maxFlow = 0;
        List<Integer> augmentingPath;

        while ((augmentingPath = findAugmentingPath(network)) != null) {
            int bottleneck = findBottleneckCapacity(network, augmentingPath);
            updateResidualCapacities(network, augmentingPath, bottleneck);
            maxFlow += bottleneck;
            printAugmentingPath(augmentingPath, bottleneck);
        }
        return maxFlow;
    }

    /**
     * Finds an augmenting path using BFS and returns the path as a list of node indices
     *
     * @param network the flow network
     * @return the augmenting path or null if none exists
     */
    private static List<Integer> findAugmentingPath(FlowNetwork network) {
        int[] parent = new int[network.getNumberOfNodes()];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(network.getSourceNode());
        parent[network.getSourceNode()] = -2;  // Special value to mark source

        boolean foundPath = false;
        while (!queue.isEmpty() && !foundPath) {
            int currentNode = queue.poll();

            for (Edge edge : network.getAdjacencyList().get(currentNode)) {
                int nextNode = edge.getTargetNode();
                if (parent[nextNode] == -1 && edge.getResidualCapacity() > 0) {
                    parent[nextNode] = currentNode;
                    queue.add(nextNode);

                    // If we've reached the sink, we've found a path
                    if (nextNode == network.getSinkNode()) {
                        foundPath = true;
                        break;
                    }
                }
            }
        }

        // If we found a path to the sink, reconstruct and return it
        if (parent[network.getSinkNode()] != -1) {
            return buildAugmentedPathFromParent(parent, network.getSinkNode());
        }

        return null;  // No augmenting path found
    }

    /**
     * Build the augmenting path from the parent array
     *
     * @param parent the parent array
     * @param sinkNode the sink node
     * @return the augmenting path as a list of node indices
     */
    private static List<Integer> buildAugmentedPathFromParent(int[] parent, int sinkNode) {
        List<Integer> path = new ArrayList<Integer>();
        for (int node = sinkNode; node != -2; node = parent[node]) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Finds the bottleneck capacity along the given path
     *
     * @param network the flow network
     * @param path the augmenting path
     * @return the bottleneck capacity
     */
    private static int findBottleneckCapacity(FlowNetwork network, List<Integer> path) {
        int bottleneckCapacity = Integer.MAX_VALUE;
        for (int i = 0; i < path.size() - 1; i++) {
            int fromNode = path.get(i);
            int toNode = path.get(i + 1);
            for (Edge edge : network.getAdjacencyList().get(fromNode)) {
                if (edge.getTargetNode() == toNode && edge.getResidualCapacity() > 0) {
                    bottleneckCapacity = Math.min(bottleneckCapacity, edge.getResidualCapacity());
                    break;  // Found the right edge, no need to keep searching
                }
            }
        }
        return bottleneckCapacity;
    }

    /**
     * Updates residual capacities along the augmenting path
     *
     * @param network the flow network
     * @param path the augmenting path
     * @param bottleneckCapacity the bottleneck capacity
     */
    private static void updateResidualCapacities(FlowNetwork network, List<Integer> path, int bottleneckCapacity) {
        for (int i = 0; i < path.size() - 1; i++) {
            int fromNode = path.get(i);
            int toNode = path.get(i + 1);
            boolean edgeFound = false;

            // Update forward edge
            for (Edge edge : network.getAdjacencyList().get(fromNode)) {
                if (edge.getTargetNode() == toNode && edge.getResidualCapacity() > 0) {
                    edge.augmentFlow(bottleneckCapacity);
                    edgeFound = true;
                    break;
                }
            }

            if (!edgeFound) {
                // This should not happen if our path finding is correct
                System.err.println("Error: Could not find edge from " + fromNode + " to " + toNode);
            }
        }
    }

    /**
     * Prints the augmenting path and its bottleneck capacity.
     *
     * @param path the augmenting path
     * @param bottleneckCapacity the bottleneck capacity
     */
    private static void printAugmentingPath(List<Integer> path, int bottleneckCapacity) {
        System.out.println("Augmenting path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i != path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println(" | Bottleneck Capacity: " + bottleneckCapacity);
    }
}