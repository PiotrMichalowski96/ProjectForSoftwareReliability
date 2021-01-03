package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.NoprojectApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

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

    @Test
    public void shouldPassAllRoutes() throws Exception {
        assertEquals(ServiceStatus.Started, camelContext.getStatus());

        mockJelinskiMorandaEndpoint.expectedBodiesReceived("{\"timeValues\":[1,2],\"model\":\"JelinskiMorandaModel\",\"estimatorN\":0.0,\"estimatorOmega\":0.0,\"expectedTime\":0.0}");
        mockSchickWolvertonEndpoint.expectedBodiesReceived("{\"timeValues\":[1,2],\"model\":\"SchickWolvertonModel\",\"estimatorN\":0.0,\"estimatorOmega\":0.0,\"expectedTime\":0.0}");

        start.sendBody("{\"timeValues\":[1,2]}");
        logger.info(stepDoneMessage("Send Body"));

        MockEndpoint.assertIsSatisfied(camelContext);
    }

    @Test
    public void shouldPassErrorRoute() throws InterruptedException {
        assertEquals(ServiceStatus.Started, camelContext.getStatus());

        mockErrorResponseEndpoint.expectedBodiesReceived("{\"exceptionType\":\"UnexpectedErrorsDataException\",\"message\":\"Wrong Data: time values are not sorted\",\"inputData\":{\"timeValues\":[2,1]}}");

        start.sendBody("{\"timeValues\":[2,1]}");
        logger.info(stepDoneMessage("Send Body"));

        MockEndpoint.assertIsSatisfied(camelContext);
    }
}
