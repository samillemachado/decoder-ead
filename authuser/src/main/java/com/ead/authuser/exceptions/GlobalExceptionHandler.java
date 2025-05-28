package com.ead.authuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRecordResponse> handleNotFoundException(final NotFoundException ex) {
        ErrorRecordResponse errorResponse = new ErrorRecordResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConstranintViolationException.class)
    public ResponseEntity<ErrorRecordResponse> handleConstranintViolationException(final ConstranintViolationException ex) {
        ErrorRecordResponse errorResponse = new ErrorRecordResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
