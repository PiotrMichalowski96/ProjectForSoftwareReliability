package com.michalowski.piotr.noproject.exception;

import com.michalowski.piotr.noproject.model.InputData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private String exceptionType;
    private String message;
    private InputData inputData;
}
