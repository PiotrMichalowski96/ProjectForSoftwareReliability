package com.michalowski.piotr.noproject.model;

import lombok.*;

import java.util.List;

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
