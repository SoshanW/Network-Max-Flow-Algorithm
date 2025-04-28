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

import java.io.File;
import java.io.IOException;

import java.util.*;


public class Main {

    private static final String INPUT_DIRECTORY = "input";
    private static final String DEFAULT_FILE = "network.txt";

    /**
     * Main method to run the program
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    processDefaultFile();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            // No need to wait for user input here anymore as requested
        }

        scanner.close();
    }

    /**
     * Displays the menu options
     */
    private static void displayMenu() {
        System.out.println("\n===== NETWORK FLOW ALGORITHM MENU =====");
        System.out.println("1. Run default dataset (network.txt)");
        System.out.println("2. Run all files in input folder");
        System.out.println("3. Run specific file from input folder");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    /**
     * Gets a valid user choice from the scanner
     *
     * @param scanner the input scanner
     * @return the user's choice as an integer
     */
    private static int getUserChoice(Scanner scanner) {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number (1-4): ");
            }
        }

        return choice;
    }

    /**
     * Processes the default file (network.txt)
     */
    private static void processDefaultFile() {
        String filename = INPUT_DIRECTORY + File.separator + DEFAULT_FILE;
        System.out.println("\nProcessing default file: " + filename);

        try {
            processFile(filename);
            System.out.println("\nDefault file processing complete.");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }




    /**
     * Processes a single file with the Ford-Fulkerson algorithm
     *
     * @param filename the file to process
     * @throws IOException if the file cannot be read
     */
    private static void processFile(String filename) throws IOException {
        System.out.println("Loading flow network from: " + filename);
        FlowNetwork flowNetwork = Parser.parseFlowNetwork(filename);
        System.out.println("Number of nodes: " + flowNetwork.getNumberOfNodes());
        System.out.println("Source node: " + flowNetwork.getSourceNode());
        System.out.println("Sink node: " + flowNetwork.getSinkNode());

        System.out.println("\nCalculating maximum flow...");
        int maxFlow = FordFulkerson.computeMaximumFlow(flowNetwork);
        System.out.println("\nMaximum flow: " + maxFlow);
    }


}