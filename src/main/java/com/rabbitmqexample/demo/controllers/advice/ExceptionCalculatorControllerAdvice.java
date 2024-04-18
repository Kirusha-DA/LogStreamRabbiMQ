package com.rabbitmqexample.demo.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rabbitmqexample.demo.exceptions.GuidNotFoundException;

@RestControllerAdvice
public class ExceptionCalculatorControllerAdvice {
    
    @ExceptionHandler(GuidNotFoundException.class)
    public ResponseEntity<?> exceptionGuidNotFoundHandler(GuidNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .build();
    }
}
