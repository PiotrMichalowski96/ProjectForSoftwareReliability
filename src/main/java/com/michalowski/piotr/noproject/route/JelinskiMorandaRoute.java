package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.processor.JelinskiMorandaProcessor;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.michalowski.piotr.noproject.route.common.Messages.*;

@Slf4j
@Component
public class JelinskiMorandaRoute extends RouteBuilder {

    private final static String ROUTE_ID = "jelinski-miranda-model-subroute";
    public final static String INPUT_URI = "direct:jelinski-miranda-subroute-input";

    private final String outputUri;

    private final JelinskiMorandaProcessor jelinskiMorandaProcessor;

    @Autowired
    public JelinskiMorandaRoute(@Value("${output.model.jm}") String outputUri,
                                JelinskiMorandaProcessor jelinskiMorandaProcessor) {
        this.outputUri = outputUri;
        this.jelinskiMorandaProcessor = jelinskiMorandaProcessor;
    }

    @Override
    public void configure() throws Exception {

        from(INPUT_URI)
            .routeId(ROUTE_ID)
            .setExchangePattern(ExchangePattern.InOnly)
            .id(STEP_START_JM_SUBROUTE)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(readFrom(INPUT_URI)))

            .bean(jelinskiMorandaProcessor)
            .id(STEP_JELINSKI_MORANDA_PROCESSOR)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_VALIDATION_PROCESSOR))

            .marshal().json()
            .id(STEP_MARSHALLING_JM_ESTIMATION_DATA)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MARSHALLING_JM_ESTIMATION_DATA))

            .to(outputUri);
    }
}
