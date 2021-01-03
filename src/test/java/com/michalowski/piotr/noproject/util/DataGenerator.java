package com.michalowski.piotr.noproject.util;

import com.michalowski.piotr.noproject.model.ErrorData;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class DataGenerator {

    public static ErrorData generateErrorDataWithSortedTimeValues(int elementsNumber) {
        List<Integer> timeValues = IntStream.rangeClosed(1, elementsNumber).boxed().collect(Collectors.toList());
        return new ErrorData(timeValues);
    }
}
