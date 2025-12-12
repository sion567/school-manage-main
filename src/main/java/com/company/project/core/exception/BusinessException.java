package com.company.project.core.exception;

public class BusinessException extends RuntimeException {
    private int errorCode;

    public BusinessException(String message) {
        super(message);
        this.errorCode = 400;
    }

    public BusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 400;
    }

    public BusinessException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
