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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                    processAllFiles(scanner);
                    break;
                case 3:
                    processSpecificFile(scanner);
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
     * Processes all files in the input directory with yes/no confirmation between files
     *
     * @param scanner the input scanner to use for user input
     */
    private static void processAllFiles(Scanner scanner) {
        try {
            List<String> inputFiles = getInputFiles();

            if (inputFiles.isEmpty()) {
                System.out.println("No files found in the input directory.");
                return;
            }

            System.out.println("\nBeginning batch processing of files in input folder:");

            for (int i = 0; i < inputFiles.size(); i++) {
                String currentFile = inputFiles.get(i);
                System.out.println("\n===== Processing file " + (i+1) + "/" + inputFiles.size() + ": " + currentFile + " =====");

                try {
                    processFile(INPUT_DIRECTORY + File.separator + currentFile);
                } catch (Exception e) {
                    System.out.println("ERROR processing " + currentFile + ": " + e.getMessage());
                }

                // If this is the last file, break out of the loop
                if (i == inputFiles.size() - 1) {
                    System.out.println("\nAll files processed.");
                    break;
                }

                // Ask if the user wants to process the next file
                String nextFile = inputFiles.get(i + 1);
                System.out.println("\nNext file: " + nextFile);
                boolean validResponse = false;
                while (!validResponse) {
                    System.out.print("Do you want to process this file? (y/n): ");
                    String response = scanner.nextLine().trim().toLowerCase();

                    if (response.equals("y") || response.equals("yes")) {
                        validResponse = true;
                        // Continue to the next iteration
                    } else if (response.equals("n") || response.equals("no")) {
                        validResponse = true;
                        System.out.println("Stopping batch processing and returning to menu.");
                        return; // Return to the menu
                    } else {
                        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not access input directory. " + e.getMessage());
        }
    }

    /**
     * Processes a specific file chosen by the user
     *
     * @param scanner the input scanner
     */
    private static void processSpecificFile(Scanner scanner) {
        try {
            List<String> inputFiles = getInputFiles();

            if (inputFiles.isEmpty()) {
                System.out.println("No files found in the input directory.");
                return;
            }

            System.out.println("\nAvailable files in input folder:");
            for (int i = 0; i < inputFiles.size(); i++) {
                System.out.println((i + 1) + ". " + inputFiles.get(i));
            }

            System.out.print("\nEnter file number or filename: ");
            String input = scanner.nextLine().trim();

            String selectedFile;
            try {
                int fileIndex = Integer.parseInt(input) - 1;
                if (fileIndex >= 0 && fileIndex < inputFiles.size()) {
                    selectedFile = inputFiles.get(fileIndex);
                } else {
                    System.out.println("Invalid file number.");
                    return;
                }
            } catch (NumberFormatException e) {
                // User entered a filename instead of a number
                selectedFile = input;
                if (!inputFiles.contains(selectedFile)) {
                    System.out.println("File '" + selectedFile + "' not found in input directory.");
                    return;
                }
            }

            String filename = INPUT_DIRECTORY + File.separator + selectedFile;
            System.out.println("\nProcessing file: " + filename);
            try {
                processFile(filename);
                System.out.println("\nFile processing complete.");
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("ERROR: Could not access input directory. " + e.getMessage());
        }
    }

    /**
     * Gets a list of all files in the input directory, sorted in natural order
     *
     * @return List of filenames in the input directory
     * @throws IOException if the directory cannot be accessed
     */
    private static List<String> getInputFiles() throws IOException {
        Path inputPath = Paths.get(INPUT_DIRECTORY);

        if (!Files.exists(inputPath)) {
            System.out.println("Input directory not found. Creating directory.");
            Files.createDirectories(inputPath);
            return List.of();
        }

        try (Stream<Path> paths = Files.list(inputPath)) {
            List<String> fileNames = paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.endsWith(".txt"))
                    .collect(Collectors.toList());

            // Sort filenames using natural order comparator
            Collections.sort(fileNames, new NaturalOrderComparator());

            return fileNames;
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

    /**
     * A comparator that implements natural ordering for strings.
     * This will sort strings like "bridge_1.txt" before "bridge_2.txt" and "bridge_10.txt" after "bridge_9.txt"
     */
    static class NaturalOrderComparator implements Comparator<String> {
        private static final Pattern NUMBERS = Pattern.compile("(\\d+)");

        @Override
        public int compare(String s1, String s2) {
            // Find all numbers in both strings
            Matcher m1 = NUMBERS.matcher(s1);
            Matcher m2 = NUMBERS.matcher(s2);

            // Position where we are in each string
            int pos1 = 0;
            int pos2 = 0;

            while (true) {
                // Find the next number in each string
                boolean b1 = m1.find(pos1);
                boolean b2 = m2.find(pos2);

                // If no more numbers in one or both strings
                if (!b1 && !b2) {
                    // No more numbers, compare the remaining text
                    return s1.substring(pos1).compareTo(s2.substring(pos2));
                }
                if (!b1) {
                    return -1; // s1 has no more numbers, so it comes first
                }
                if (!b2) {
                    return 1;  // s2 has no more numbers, so it comes first
                }

                // Compare the text before the numbers
                String before1 = s1.substring(pos1, m1.start());
                String before2 = s2.substring(pos2, m2.start());
                int comp = before1.compareTo(before2);
                if (comp != 0) {
                    return comp;
                }

                // Compare the numbers themselves
                int num1 = Integer.parseInt(m1.group(1));
                int num2 = Integer.parseInt(m2.group(1));
                comp = Integer.compare(num1, num2);
                if (comp != 0) {
                    return comp;
                }

                // Update positions and continue
                pos1 = m1.end();
                pos2 = m2.end();
            }
        }
    }
}