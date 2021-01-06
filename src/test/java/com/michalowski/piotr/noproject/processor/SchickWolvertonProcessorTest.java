package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.InputData;
import com.michalowski.piotr.noproject.model.EstimationData;
import com.michalowski.piotr.noproject.model.EstimationModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SchickWolvertonProcessorTest {

    private final SchickWolvertonProcessor processor = new SchickWolvertonProcessor();

    @Test
    public void shouldReturnCorrectEstimationData() {
        //given
        List<Integer> timeValues = List.of(1, 4, 7, 3, 5);
        double accuracy = 0.1;
        InputData inputData = new InputData(timeValues, accuracy);
        Double expectedEstimatorN = 7.0;
        Double expectedEstimatorFi = 0.021857142857142856;
        Double expectedTimeValue = 8.477411079541396;
        EstimationData expectedEstimationData = EstimationData.builder()
                .inputData(inputData)
                .model(EstimationModel.SchickWolvertonModel)
                .estimatorN(expectedEstimatorN)
                .estimatorFi(expectedEstimatorFi)
                .expectedTime(expectedTimeValue)
                .build();

        //when
        EstimationData resultEstimationData = processor.process(inputData);

        //then
        assertThat(resultEstimationData).isEqualToComparingFieldByField(expectedEstimationData);
    }
}
