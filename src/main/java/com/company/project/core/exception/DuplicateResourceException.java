package com.company.project.core.exception;

public class DuplicateResourceException extends BusinessException {
    private String resource;

    public DuplicateResourceException(String message, String resource) {
        super(message, 409);
        this.resource = resource;
    }

    public DuplicateResourceException(String message, String resource, Throwable cause) {
        super(message, 409, cause);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}