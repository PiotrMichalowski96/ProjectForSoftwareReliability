package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.InputData;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ValidationProcessor {

    private final static int MINIMUM_ELEMENTS = 100;
    private final static double MINIMAL_RELATIVE_ERROR = 0.001;

    @Handler
    public void process(InputData inputData) throws UnexpectedErrorsDataException {
        List<Integer> errors = inputData.getTimeValues();
        double accuracy = inputData.getAccuracy();
        checkElementNumber(errors);
        checkIfElementsArePositive(errors);
        checkIfAccuracyIsPositive(accuracy);
        checkIfAccuracyMinimal(accuracy);
    }

    private void checkIfAccuracyMinimal(double accuracy) {
        if(accuracy < MINIMAL_RELATIVE_ERROR) {
            String message = String.format("Wrong Data: accuracy value is less than minimum: %.3f",MINIMAL_RELATIVE_ERROR);
            logger.error(message);
            throw new UnexpectedErrorsDataException(message);
        }
    }

    private void checkIfAccuracyIsPositive(double accuracy) {
        if(accuracy <= 0) {
            logger.error("Accuracy is not positive");
            throw new UnexpectedErrorsDataException("Wrong Data: accuracy is not positive");
        }
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
}
