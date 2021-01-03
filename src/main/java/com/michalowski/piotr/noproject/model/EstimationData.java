package com.michalowski.piotr.noproject.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class EstimationData {
    List<Integer> timeValues;
    EstimationModel model;
    Double estimatorN;
    Double estimatorOmega;
    Double expectedTime;
}
