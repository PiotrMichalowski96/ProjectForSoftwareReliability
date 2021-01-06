package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.InputData;
import com.michalowski.piotr.noproject.model.EstimationData;
import com.michalowski.piotr.noproject.model.EstimationModel;
import com.michalowski.piotr.noproject.solver.SchickWolvertonSolver;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchickWolvertonProcessor {

    @Handler
    public EstimationData process(InputData inputData) {
        List<Integer> timeValues = inputData.getTimeValues();
        double accuracy = inputData.getAccuracy();

        SchickWolvertonSolver solver = new SchickWolvertonSolver(timeValues, accuracy);
        Double estimatorN = solver.getNParameter().doubleValue();
        Double estimatorOmega = solver.getFiParameter().doubleValue();
        Double expectedTime = solver.getExpectedTime().doubleValue();

        return EstimationData.builder()
                .inputData(inputData)
                .model(EstimationModel.SchickWolvertonModel)
                .estimatorN(estimatorN)
                .estimatorFi(estimatorOmega)
                .expectedTime(expectedTime)
                .build();
    }
}
