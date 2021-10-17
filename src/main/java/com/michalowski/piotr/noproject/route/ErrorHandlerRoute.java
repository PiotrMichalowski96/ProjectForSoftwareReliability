package com.michalowski.piotr.noproject.route;

import com.michalowski.piotr.noproject.processor.ErrorHandlerProcessor;
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
public class ErrorHandlerRoute extends RouteBuilder {

    private final static String ROUTE_ID = "error-handler-route";
    public static final String ERROR_URI = "direct:error";

    @Value("${output.error}")
    private final String errorOutputUri;
    private final ErrorHandlerProcessor errorHandlerProcessor;

    @Override
    public void configure() throws Exception {

        from(ERROR_URI)
                .routeId(ROUTE_ID)
                .setExchangePattern(ExchangePattern.InOnly)

                .bean(errorHandlerProcessor)
                .id(STEP_ERROR_PROCESSING)
                .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_ERROR_PROCESSING))

                .marshal().json()
                .id(STEP_MARSHALLING_ERROR)
                .log(LoggingLevel.INFO, logger, stepDoneMessage(STEP_MARSHALLING_ERROR))

                .to(errorOutputUri);
    }
}
