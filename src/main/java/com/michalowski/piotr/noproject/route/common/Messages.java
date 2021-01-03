package com.michalowski.piotr.noproject.route.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {
    public static final String STEP_START_BASE_ROUTE = "Start base route";
    public static final String STEP_START_JM_SUBROUTE = "Start Jelinski-Miranda subroute";
    public static final String STEP_START_SW_SUBROUTE = "Start Schick-Wolverton subroute";
    public static final String STEP_UNMARSHALLING = "Unmarshalling json file";
    public static final String STEP_VALIDATION_PROCESSOR = "Validation processor";
    public static final String STEP_JELINSKI_MORANDA_PROCESSOR = "Jelinski-Moranda model estimation processor";
    public static final String STEP_SCHICK_WOLVERTON_PROCESSOR = "Schick-Wolverton model estimation processor";
    public static final String STEP_MARSHALLING_JM_ESTIMATION_DATA = "Marshalling Jelinski-Moranda model estimation data to json";
    public static final String STEP_MARSHALLING_SW_ESTIMATION_DATA = "Marshalling Schick-Wolverton model estimation data to json";
    public static final String STEP_MARSHALLING_ERROR = "Marshalling error to json";
    public static final String STEP_ERROR_PROCESSING = "Preparing Error Response";

    public static String readFrom(String inputUri) {
        StringBuilder sb = new StringBuilder();
        sb.append("Read from: ");
        sb.append(inputUri);
        return sb.toString();
    }

    public static String stepDoneMessage(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("STEP DONE: ");
        sb.append(message);
        return sb.toString();
    }
}
