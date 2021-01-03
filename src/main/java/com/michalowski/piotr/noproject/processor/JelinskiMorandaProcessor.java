package com.michalowski.piotr.noproject.processor;

import com.michalowski.piotr.noproject.model.ErrorData;
import com.michalowski.piotr.noproject.model.EstimationData;
import com.michalowski.piotr.noproject.model.EstimationModel;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JelinskiMorandaProcessor {

    @Handler
    public EstimationData process(ErrorData errorData) {
        List<Integer> timeValues = errorData.getTimeValues();

        //TODO: some processing to receive N, Omega and Expected time
        Double estimatorN = 0.0;
        Double estimatorOmega = 0.0;
        Double expectedTime = 0.0;

        return EstimationData.builder()
                .timeValues(timeValues)
                .model(EstimationModel.JelinskiMorandaModel)
                .estimatorN(estimatorN)
                .estimatorOmega(estimatorOmega)
                .expectedTime(expectedTime)
                .build();
    }
}
