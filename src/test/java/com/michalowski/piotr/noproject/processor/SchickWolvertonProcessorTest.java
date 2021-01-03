package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.ErrorData;
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
        //TODO: change values
        List<Integer> timeValues = List.of(1,2);
        ErrorData inputErrorData = new ErrorData(timeValues);
        Double expectedEstimatorN = 0.0;
        Double expectedEstimatorOmega = 0.0;
        Double expectedTimeValue = 0.0;
        EstimationData expectedEstimationData = EstimationData.builder()
                .timeValues(timeValues)
                .model(EstimationModel.SchickWolvertonModel)
                .estimatorN(expectedEstimatorN)
                .estimatorOmega(expectedEstimatorOmega)
                .expectedTime(expectedTimeValue)
                .build();

        //when
        EstimationData resultEstimationData = processor.process(inputErrorData);

        //then
        assertThat(resultEstimationData).isEqualToComparingFieldByField(expectedEstimationData);
    }
}
