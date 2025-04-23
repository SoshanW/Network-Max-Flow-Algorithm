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
            int lineNumber = 1; //for error handling purposes if in the sense a user adds invalid line number for input.txt
            while((line = reader.readLine()) != null) {
                lineNumber++;
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != 3) {
                    System.out.println("Warning: Line " + lineNumber + " is malformed and will be ignored. Expected 3 values but got " + tokens.length);
                    continue; //ignore malformed lines
                }

                try {
                    int fromNode = Integer.parseInt(tokens[0]);
                    int toNode = Integer.parseInt(tokens[1]);
                    int capacity = Integer.parseInt(tokens[2]);

                    // Validate node indices before adding the edge
                    if (fromNode < 0 || fromNode >= numberOfNodes) {
                        System.out.println("Warning: Line " + lineNumber + " has invalid 'from' node " + fromNode +
                                ". Node indices must be between 0 and " + (numberOfNodes - 1) + ". This edge will be ignored.");
                        continue;
                    }
                    if (toNode < 0 || toNode >= numberOfNodes) {
                        System.out.println("Warning: Line " + lineNumber + " has invalid 'to' node " + toNode +
                                ". Node indices must be between 0 and " + (numberOfNodes - 1) + ". This edge will be ignored.");
                        continue;
                    }
                    if (capacity < 0) {
                        System.out.println("Warning: Line " + lineNumber + " has negative capacity " + capacity +
                                ". Capacity must be non-negative. This edge will be ignored.");
                        continue;
                    }

                    network.addEdge(fromNode, toNode, capacity);
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Line " + lineNumber + " contains non-integer values and will be ignored.");
                }
            }

            return network;
        }
    }
}
