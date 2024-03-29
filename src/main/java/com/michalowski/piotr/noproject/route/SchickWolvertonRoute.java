package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.processor.SchickWolvertonProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.michalowski.piotr.noproject.route.common.Messages.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchickWolvertonRoute extends RouteBuilder {

    private final static String ROUTE_ID = "schick-wolverton-model-subroute";
    public final static String INPUT_URI = "direct:schick-wolverton-subroute-input";

    @Value("${output.model.sw}")
    private final String outputUri;
    private final SchickWolvertonProcessor schickWolvertonProcessor;

    @Override
    public void configure() throws Exception {

        from(INPUT_URI)
            .routeId(ROUTE_ID)
            .setExchangePattern(ExchangePattern.InOnly)
            .id(STEP_START_SW_SUBROUTE)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(readFrom(INPUT_URI)))

            .bean(schickWolvertonProcessor)
            .id(STEP_SCHICK_WOLVERTON_PROCESSOR)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_SCHICK_WOLVERTON_PROCESSOR))

            .marshal().json()
            .id(STEP_MARSHALLING_SW_ESTIMATION_DATA)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MARSHALLING_SW_ESTIMATION_DATA))

            .to(outputUri);
    }
}
