package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.InputData;
import com.michalowski.piotr.noproject.model.EstimationData;
import com.michalowski.piotr.noproject.model.EstimationModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JelinskiMorandaProcessorTest {

    private final JelinskiMorandaProcessor processor = new JelinskiMorandaProcessor();

    @Test
    public void shouldReturnCorrectEstimationData() {
        //given
        List<Integer> timeValues = List.of(1, 4, 7, 3, 5);
        double accuracy = 0.01;
        InputData inputData = new InputData(timeValues, accuracy);
        Double expectedEstimatorN = 8.0;
        Double expectedEstimatorFi = 0.04424778761061947;
        Double expectedTimeValue = 11.3;
        EstimationData expectedEstimationData = EstimationData.builder()
                .inputData(inputData)
                .model(EstimationModel.JelinskiMorandaModel)
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
