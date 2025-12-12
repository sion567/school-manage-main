package com.company.project.core.exception;

import java.util.Map;

public class CustomValidationException extends RuntimeException {
    private Map<String, String> fieldErrors;
    private String errorCode;

    public CustomValidationException(String message) {
        super(message);
        this.errorCode = "VALIDATION_ERROR";
    }

    public CustomValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
        this.errorCode = "VALIDATION_ERROR";
    }

    public CustomValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomValidationException(String message, Map<String, String> fieldErrors, String errorCode) {
        super(message);
        this.fieldErrors = fieldErrors;
        this.errorCode = errorCode;
    }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public String getErrorCode() { return errorCode; }
}
