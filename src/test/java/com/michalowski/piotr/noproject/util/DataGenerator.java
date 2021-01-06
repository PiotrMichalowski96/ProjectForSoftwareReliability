package com.michalowski.piotr.noproject.util;

import com.michalowski.piotr.noproject.model.InputData;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class DataGenerator {

    public static InputData generateRandomInputData(int accuracyRange, int rangeFrom, int rangeSize, int elementsNumber) {
        Random random = new Random();
        List<Integer> timeValues = random.ints(rangeFrom, (rangeFrom + rangeSize))
                .limit(elementsNumber)
                .boxed()
                .collect(Collectors.toList());
        double accuracy = accuracyRange * random.nextDouble();
        return new InputData(timeValues, accuracy);
    }
}
