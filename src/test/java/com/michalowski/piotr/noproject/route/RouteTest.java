package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.NoprojectApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.michalowski.piotr.noproject.route.common.Messages.stepDoneMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@CamelSpringTest
@ContextConfiguration(classes = {NoprojectApplication.class})
@TestPropertySource(locations="classpath:test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RouteTest {

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject("mock:jm")
    protected MockEndpoint mockJelinskiMorandaEndpoint;

    @EndpointInject("mock:sw")
    protected MockEndpoint mockSchickWolvertonEndpoint;

    @EndpointInject("mock:errorHandler")
    protected MockEndpoint mockErrorResponseEndpoint;

    @Produce("direct:start")
    protected ProducerTemplate start;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void shouldPassAllRoutes(int messageNumber) throws InterruptedException, IOException {
        //given
        String inputFile = readJsonFileToString(String.format("src/test/resources/samples/input/message_%d_in.json", messageNumber));
        String jelinskiMorandaExpectedFile = readJsonFileToString(String.format("src/test/resources/samples/output/message_%d_out_jm.json", messageNumber));
        String schickWolvertonExpectedFile = readJsonFileToString(String.format("src/test/resources/samples/output/message_%d_out_sw.json", messageNumber));

        assertEquals(ServiceStatus.Started, camelContext.getStatus());
        mockJelinskiMorandaEndpoint.expectedBodiesReceived(jelinskiMorandaExpectedFile);
        mockSchickWolvertonEndpoint.expectedBodiesReceived(schickWolvertonExpectedFile);

        //when
        start.sendBody(inputFile);
        logger.info(stepDoneMessage("Send Body"));

        //then
        MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Test
    public void shouldPassErrorRoute() throws InterruptedException, IOException {
        //given
        String inputFile = readJsonFileToString("src/test/resources/samples/input/message_1_in_corrupted.json");
        String expectedFile = readJsonFileToString("src/test/resources/samples/output/message_1_out_corrupted.json");

        assertEquals(ServiceStatus.Started, camelContext.getStatus());
        mockErrorResponseEndpoint.expectedBodiesReceived(expectedFile);

        //when
        start.sendBody(inputFile);
        logger.info(stepDoneMessage("Send Body"));

        //then
        MockEndpoint.assertIsSatisfied(camelContext);
    }

    private String readJsonFileToString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
