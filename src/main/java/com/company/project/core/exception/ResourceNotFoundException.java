package com.company.project.core.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BusinessException {
    private final String resource;

    public ResourceNotFoundException(String message, String resource) {
        super(message, 404);
        this.resource = resource;
    }

    public ResourceNotFoundException(String message, String resource, Throwable cause) {
        super(message, 404, cause);
        this.resource = resource;
    }

}
