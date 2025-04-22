/**
 * Implements the Ford-Fulkerson method Edmonds-Karp Algorithm for maximum flow
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.algorithm;


import com.algo.network.flow.network.FlowNetwork;

import java.util.List;

/**
 * The FordFulkerson class provides a method to compute the maximum flow in a  flow network using the Edmonds-Karp algorithm
 */
public class FordFulkerson {

    public static int computeMaximumFlow(FlowNetwork network){
        int maxFlow = 0;
        List<Integer> augmentingPath;

        while((augmentingPath = findAugementingPath(network))!=null){
            //have to find bottleneck
            //update the redisual path
            //add bottle neck to the maxflow
            //print augmenting path
        }
    }


}
