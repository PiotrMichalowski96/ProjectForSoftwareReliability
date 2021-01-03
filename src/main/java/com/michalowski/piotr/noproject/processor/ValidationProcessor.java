package com.michalowski.piotr.noproject.processor;

import com.google.common.collect.Ordering;
import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.ErrorData;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

@Slf4j
@Component
public class ValidationProcessor {

    //TODO: change minimum elements value
    private final static int MINIMUM_ELEMENTS = 2;

    @Handler
    public void process(ErrorData errorData) throws UnexpectedErrorsDataException {
        List<Integer> errors = errorData.getTimeValues();
        checkElementNumber(errors);
        checkIfElementsArePositive(errors);
        checkIfElementsAreSorted(errors);
    }

    private void checkElementNumber(List<Integer> elements) throws UnexpectedErrorsDataException {
        if(elements.size() < MINIMUM_ELEMENTS) {
            String message = String.format("Wrong Data: number of time values is less than minimum: %d",MINIMUM_ELEMENTS);
            logger.error(message);
            throw new UnexpectedErrorsDataException(message);
        }
    }

    private void checkIfElementsArePositive(List<Integer> elements) throws UnexpectedErrorsDataException {
        boolean hasNegative = elements.stream().anyMatch(element -> element <= 0);
        if(hasNegative) {
            logger.error("Time values are not positive");
            throw new UnexpectedErrorsDataException("Wrong Data: time values are not positive");
        }
    }

    private void checkIfElementsAreSorted(List<Integer> elements) throws UnexpectedErrorsDataException {
        boolean isSorted = Ordering.natural().isOrdered(elements);
        if(!isSorted) {
            logger.error("Time values are not sorted");
            throw new UnexpectedErrorsDataException("Wrong Data: time values are not sorted");
        }
    }
}
