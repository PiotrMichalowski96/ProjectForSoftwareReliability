package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.exception.UnexpectedErrorsDataException;
import com.michalowski.piotr.noproject.model.ErrorData;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.michalowski.piotr.noproject.util.DataGenerator.generateErrorDataWithSortedTimeValues;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ValidationProcessorTest {

    private static final int MINIMUM_ELEMENTS = 240;

    private final ValidationProcessor validationProcessor = new ValidationProcessor();

    @Test
    public void shouldPassValidData() throws UnexpectedErrorsDataException {
        //given
        ErrorData errorData = generateErrorDataWithSortedTimeValues(MINIMUM_ELEMENTS);

        //whenThen
        validationProcessor.process(errorData);
    }

    @Test
    public void shouldThrowExceptionIfEmptyTimeValuesList() {
        //given
        List<Integer> timeValues = Collections.emptyList();
        ErrorData errorData = new ErrorData(timeValues);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(errorData));
    }

    @Test
    public void shouldThrowExceptionIfElementsNumberIsLessThan240() {
        //given
        ErrorData errorData = generateErrorDataWithSortedTimeValues(200);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(errorData));
    }

    @Test
    public void shouldThrowExceptionIfContainsNegativeTimeValues() {
        //given
        ErrorData errorData = generateErrorDataWithSortedTimeValues(MINIMUM_ELEMENTS);
        List<Integer> timeValues = errorData.getTimeValues();
        timeValues.add(-5);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(errorData));
    }

    @Test
    public void shouldThrowExceptionIfValuesAreNotSorted() {
        //given
        ErrorData errorData = generateErrorDataWithSortedTimeValues(MINIMUM_ELEMENTS);
        List<Integer> timeValues = errorData.getTimeValues();
        Collections.shuffle(timeValues);

        //whenThen
        assertThatExceptionOfType(UnexpectedErrorsDataException.class)
                .isThrownBy(() -> validationProcessor.process(errorData));
    }

}
