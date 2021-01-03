package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.ErrorResponse;
import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.ErrorData;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;

import static com.michalowski.piotr.noproject.util.DataGenerator.generateErrorDataWithSortedTimeValues;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandlerProcessorTest {

    private final ErrorHandlerProcessor processor = new ErrorHandlerProcessor();

    private static Exchange createExchange(Exception exception, ErrorData errorData) {
        CamelContext context = new DefaultCamelContext();
        Exchange exchange = new DefaultExchange(context);
        exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);
        exchange.getIn().setBody(errorData);
        return exchange;
    }

    @Test
    public void shouldReturnCorrectErrorResponse() throws Exception {
        //given
        UnexpectedErrorsDataException exception = new UnexpectedErrorsDataException("message");
        ErrorData errorData = generateErrorDataWithSortedTimeValues(2);
        Exchange exchange = createExchange(exception, errorData);
        ErrorResponse expectedResponse = new ErrorResponse(exception.getClass().getSimpleName(),
                exception.getMessage(), errorData);

        //when
        ErrorResponse resultResponse = processor.process(exchange);

        //then
        assertThat(resultResponse).isEqualToComparingFieldByField(expectedResponse);
    }
}
