/**
 * Entry point for the maximum network flow application
 * Author: Soshan Wijayarathne
 * IIT id: 20230427
 * UOW id: w2051662
 */

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

//The expected outputs were each test file were AI generated. These outputs will be used to check whether the output given by the code is accurate
                Arguments.of("src/test/resources/test1.txt", 3),
                Arguments.of("src/test/resources/test2.txt", 5),
                Arguments.of("src/test/resources/test3.txt", 0),
                Arguments.of("src/test/resources/test4.txt", 8),
                Arguments.of("src/test/resources/test5.txt", 15),
                Arguments.of("src/test/resources/test6.txt", 2),
                Arguments.of("src/test/resources/test7.txt", 100),
                Arguments.of("src/test/resources/test8.txt", 2000),
                Arguments.of("src/test/resources/test9.txt", 0),
                Arguments.of("src/test/resources/test10.txt", 23)

        );
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testMaxFlow(String inputFile, int expectedFlow) throws Exception {
        // Arrange: Parse the network from file
        FlowNetwork network = Parser.parseFlowNetwork(inputFile);

        // Compute maximum flow
        int actualFlow = FordFulkerson.computeMaximumFlow(network);

        // Verify expected result
        assertEquals(expectedFlow, actualFlow,
                "Incorrect max flow for " + inputFile);
    }
}
