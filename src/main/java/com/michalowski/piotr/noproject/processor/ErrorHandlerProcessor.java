package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.ErrorResponse;
import com.michalowski.piotr.noproject.model.InputData;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlerProcessor {

    @Handler
    public ErrorResponse process(Exchange exchange) throws Exception {
        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        InputData inputData = (InputData) exchange.getIn().getBody();
        String exceptionType = cause.getClass().getSimpleName();
        String message = cause.getMessage();

        return new ErrorResponse(exceptionType, message, inputData);
    }
}
