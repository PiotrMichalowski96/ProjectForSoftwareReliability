package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.ErrorResponse;
import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.InputData;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;

import static com.michalowski.piotr.noproject.util.DataGenerator.generateRandomInputData;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandlerProcessorTest {

    private final ErrorHandlerProcessor processor = new ErrorHandlerProcessor();

    private static Exchange createExchange(Exception exception, InputData inputData) {
        CamelContext context = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(context);
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);
        exchange.getIn().setBody(inputData);
        return exchange;
    }

    @Test
    public void shouldReturnCorrectErrorResponse() throws Exception {
        //given
        UnexpectedErrorsDataException exception = new UnexpectedErrorsDataException("message");
        InputData inputData = generateRandomInputData(2,2,2,2);
        Exchange exchange = createExchange(exception, inputData);
        ErrorResponse expectedResponse = new ErrorResponse(exception.getClass().getSimpleName(),
                exception.getMessage(), inputData);

        //when
        ErrorResponse resultResponse = processor.process(exchange);

        //then
        assertThat(resultResponse).isEqualToComparingFieldByField(expectedResponse);
    }
}
