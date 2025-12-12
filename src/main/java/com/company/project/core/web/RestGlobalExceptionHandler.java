package com.company.project.core.web;


import java.net.URI;
import java.time.Instant;

import com.company.project.core.exception.BusinessException;
import com.company.project.core.exception.ResourceNotFoundException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException e) {
//        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
//        detail.setTitle("Bad Request");
//        detail.setProperty("errorCategory", "Generic Exception");
//        detail.setProperty("timestamp", Instant.now());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
//    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleConnversion(ConversionFailedException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        detail.setTitle("Bad Request");
        detail.setProperty("errorCategory", "Generic Exception");
        detail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setTitle("Resource Not Found");
        detail.setProperty("errorCategory", "Generic Exception");
        detail.setProperty("timestamp", Instant.now());

//        detail.setType(URI.create("https://example.com/errors/not-found"));
//        detail.setInstance(URI.create("/users/123"));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex) {
        log.error("业务异常 => code: {}, 原因是: {}", ex.getErrorCode(), ex.getMessage());
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
        detail.setProperty("errorCode", ex.getErrorCode());
        return ResponseEntity.status(ex.getErrorCode()).body(detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
//        detail.setProperty("errorCode", ex.getErrorCode());
//        detail.setProperty("statusCode", ex.getStatusCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(detail);
    }

}
