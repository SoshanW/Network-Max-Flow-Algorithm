/**
 * Entry point for the maximum network flow application
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow;


import com.algo.network.flow.algorithm.FordFulkerson;
import com.algo.network.flow.network.FlowNetwork;
import com.algo.network.flow.parser.Parser;


public class Main {

    /**
     * Main method to run the program
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String filename = "network.txt";

        try{
            FlowNetwork flowNetwork = Parser.parseFlowNetwork(filename);
            System.out.println("Calculating maximum flow...");
            int maxFlow = FordFulkerson.computeMaximumFlow(flowNetwork);
            System.out.println("Maximum flow: " + maxFlow);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
