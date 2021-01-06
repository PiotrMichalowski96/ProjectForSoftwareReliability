package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.model.InputData;
import com.michalowski.piotr.noproject.processor.ErrorHandlerProcessor;
import com.michalowski.piotr.noproject.processor.ValidationProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.michalowski.piotr.noproject.route.common.Messages.*;

@Slf4j
@Component
public class BaseRoute extends RouteBuilder {

    private final static String ROUTE_ID = "error-data-to-estimation-route";

    private final String inputUri;

    private final ValidationProcessor validationProcessor;

    @Autowired
    public BaseRoute(@Value("${input}") String inputUri, ValidationProcessor validationProcessor) {
        this.inputUri = inputUri;
        this.validationProcessor = validationProcessor;
    }

    @Override
    public void configure() throws Exception {

        errorHandler(deadLetterChannel(ErrorHandlerRoute.ERROR_URI));

        from(inputUri)
            .routeId(ROUTE_ID)
            .setExchangePattern(ExchangePattern.InOnly)
            .id(STEP_START_BASE_ROUTE)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(readFrom(inputUri)))

            .unmarshal().json(JsonLibrary.Jackson, InputData.class)
            .id(STEP_UNMARSHALLING)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_UNMARSHALLING))

            .bean(validationProcessor)
            .id(STEP_VALIDATION_PROCESSOR)
            .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_VALIDATION_PROCESSOR))

            .wireTap(JelinskiMorandaRoute.INPUT_URI)
            .wireTap(SchickWolvertonRoute.INPUT_URI);
    }
}
