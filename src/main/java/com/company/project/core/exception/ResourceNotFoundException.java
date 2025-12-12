package com.company.project.core.exception;

public class ResourceNotFoundException extends BusinessException {
    private String resource;

    public ResourceNotFoundException(String message, String resource) {
        super(message, 404);
        this.resource = resource;
    }

    public ResourceNotFoundException(String message, String resource, Throwable cause) {
        super(message, 404, cause);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
