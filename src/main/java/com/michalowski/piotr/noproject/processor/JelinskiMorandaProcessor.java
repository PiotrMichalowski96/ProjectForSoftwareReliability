package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.InputData;
import com.michalowski.piotr.noproject.model.EstimationData;
import com.michalowski.piotr.noproject.model.EstimationModel;
import com.michalowski.piotr.noproject.solver.JelinskiMorandaSolver;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JelinskiMorandaProcessor {

    @Handler
    public EstimationData process(InputData inputData) {
        List<Integer> timeValues = inputData.getTimeValues();
        double accuracy = inputData.getAccuracy();

        JelinskiMorandaSolver solver = new JelinskiMorandaSolver(timeValues, accuracy);
        Double estimatorN = solver.getNParameter().doubleValue();
        Double estimatorOmega = solver.getFiParameter().doubleValue();
        Double expectedTime = solver.getExpectedTime().doubleValue();

        return EstimationData.builder()
                .inputData(inputData)
                .model(EstimationModel.JelinskiMorandaModel)
                .estimatorN(estimatorN)
                .estimatorFi(estimatorOmega)
                .expectedTime(expectedTime)
                .build();
    }
}
