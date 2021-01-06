package com.michalowski.piotr.noproject.model;

import lombok.*;

@Builder
@Getter
@Setter
public class EstimationData {
    InputData inputData;
    EstimationModel model;
    Double estimatorN;
    Double estimatorFi;
    Double expectedTime;
}
