/**
 * Parses a flow network from an input file
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */
package com.algo.network.flow.parser;


import com.algo.network.flow.network.FlowNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    public Parser() {
    }

    /**
     * Parses the specified field and constructs a FlowNetwork object
     *
     * @param filename the name of the input file
     * @return the constructed FlowNetwork
     * @throws IOException if an I/O error occurs
     */
    public static FlowNetwork parseFlowNetwork(String filename) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstLine = reader.readLine();
            if(firstLine == null) {
                throw new IllegalArgumentException("Input File is Empty");
            }
            int numberOfNodes = Integer.parseInt(firstLine.trim());
            int sourceNode = 0;
            int sinkNode = numberOfNodes - 1;
            FlowNetwork network = new FlowNetwork(numberOfNodes, sourceNode, sinkNode);

            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != 3) {
                    continue; //ignore malformed lines
                }
                int fromNode = Integer.parseInt(tokens[0]);
                int toNode = Integer.parseInt(tokens[1]);
                int capacity = Integer.parseInt(tokens[2]);
                network.addEdge(fromNode, toNode, capacity);
            }
            return network;
        }
    }
}
