package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.InputData;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.michalowski.piotr.noproject.util.DataGenerator.generateRandomInputData;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ValidationProcessorTest {

    private static final int MINIMUM_ELEMENTS = 100;

    private final ValidationProcessor validationProcessor = new ValidationProcessor();

    @Test
    public void shouldPassValidData() throws UnexpectedErrorsDataException {
        //given
        InputData inputData = generateRandomInputData(2, 1, 100, MINIMUM_ELEMENTS);

        //whenThen
        validationProcessor.process(inputData);
    }

    @Test
    public void shouldThrowExceptionIfEmptyTimeValuesList() {
        //given
        List<Integer> timeValues = Collections.emptyList();
        double accuracy = 0.1;
        InputData inputData = new InputData(timeValues, accuracy);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(inputData));
    }

    @Test
    public void shouldThrowExceptionIfElementsNumberIsLessThan100() {
        //given
        InputData inputData = generateRandomInputData(2, 1, 1000, 20);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(inputData));
    }

    @Test
    public void shouldThrowExceptionIfContainsNegativeTimeValues() {
        //given
        InputData inputData = generateRandomInputData(2, 1, 100, MINIMUM_ELEMENTS);
        List<Integer> timeValues = inputData.getTimeValues();
        timeValues.add(-5);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(inputData));
    }

}
