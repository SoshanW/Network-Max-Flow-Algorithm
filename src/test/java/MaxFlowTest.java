import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.stream.Stream;

import com.algo.network.flow.algorithm.FordFulkerson;
import com.algo.network.flow.network.FlowNetwork;
import com.algo.network.flow.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


public class MaxFlowTest {

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                // Format: (input-file-path, expected-max-flow)
                Arguments.of("src/test/resources/test1.txt", 3)

        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testMaxFlow(String inputFile, int expectedFlow) throws Exception {
        // Arrange: Parse the network from file
        FlowNetwork network = Parser.parseFlowNetwork(inputFile);

        // Act: Compute maximum flow
        int actualFlow = FordFulkerson.computeMaximumFlow(network);

        // Assert: Verify expected result
        assertEquals(expectedFlow, actualFlow,
                "Incorrect max flow for " + inputFile);
    }
}
