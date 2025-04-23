# Network Flow Algorithm Implementation

This project implements the Ford-Fulkerson method with the Edmonds-Karp algorithm for solving the maximum flow problem in a flow network.

## Project Overview

The maximum flow problem aims to find the maximum amount of flow that can be sent from a source to a sink in a flow network while respecting the capacity constraints of each edge. This implementation uses the Ford-Fulkerson method with the Edmonds-Karp algorithm, which guarantees a time complexity of O(V·E²) where V is the number of vertices and E is the number of edges.

## Features

- Edmonds-Karp implementation of the Ford-Fulkerson algorithm
- Input file parsing for network definition
- Detailed augmenting path visualization
- JUnit tests for verification

## Project Structure

```
network-flow-coursework/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── com.algo.network.flow/
│   │       │   ├── algorithm/
│   │       │   |    └── FordFulkerson.java
│   │       |   ├── network/
│   │       │   |   ├── Edge.java
│   │       |   │   └── FlowNetwork.java
│   │       |   ├── parser/
│   │       │   |   └── Parser.java
│   │       |   └── Main.java
│   └── test/
│       └── java/
│           └── MaxFlowTest.java
│       └── resources/
│           └── test1.txt
│           └── test2.txt
│           └── test3.txt
│           └── .....
├── input/
│   └── network.txt            
│
└── pom.xml
```

## Algorithm Implementation

The Ford-Fulkerson method with Edmonds-Karp algorithm works as follows:

1. Initialize the flow on all edges to zero
2. While there exists an augmenting path from source to sink:
    - Find an augmenting path using BFS (ensuring we find the shortest path)
    - Determine the bottleneck capacity along the path
    - Update the flow along the path by the bottleneck capacity
3. Return the sum of flows into the sink (or equivalently, out of the source)

## Input Format

The input file should follow this format:
```
<number_of_nodes>
<from_node> <to_node> <capacity>
<from_node> <to_node> <capacity>
...
```

Example (`network.txt`):
```
4
0 1 6
0 2 4
1 2 2
1 3 3
2 3 5
```

In this example:
- The network has 4 nodes (numbered 0 to 3)
- Node 0 is the source, and node 3 is the sink
- There are edges from node 0 to 1 with capacity 6, from 0 to 2 with capacity 4, etc.

## Running the Project

### Prerequisites
- Java 23 (or adjust the version in pom.xml)
- Maven

### Build
```bash
mvn clean package
```

### Run
```bash
java -cp target/Network-Flow-1.0-SNAPSHOT.jar com.algo.network.flow.Main
```

Or using Maven:
```bash
mvn exec:java
```

### Testing
```bash
mvn test
```

## Test Cases

The project includes multiple test cases to verify the correctness of the implementation:

- Basic flow networks with known maximum flow values
- Networks with no paths from source to sink
- Large capacity networks
- Complex network topologies

## Implementation Details

### Key Classes

1. **FlowNetwork.java**: Represents the flow network using adjacency lists, with methods for adding edges and tracking residual capacities.

2. **Edge.java**: Represents a directed edge in the flow network, with capacity, flow, and methods to calculate residual capacity.

3. **FordFulkerson.java**: Implements the Edmonds-Karp algorithm to find the maximum flow.

4. **Parser.java**: Parses input files to create flow network instances.

## Performance Considerations

The Edmonds-Karp implementation guarantees polynomial time complexity by using BFS to find augmenting paths. This ensures that shorter paths are considered first, leading to a time complexity of O(V·E²).

## Author

- Soshan Wijayarathne
- IIT ID: 20230427
- UOW ID: w2051662

## License

This project is provided for educational purposes.
