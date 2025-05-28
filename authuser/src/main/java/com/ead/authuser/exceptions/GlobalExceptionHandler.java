package com.ead.authuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    final String ERROR_VALIDATION_FAILED_MESSAGE = "Error: Validation failed.";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorRecordResponse> handleNotFoundException(final NotFoundException ex) {
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRecordResponse);
    }

    @ExceptionHandler(ConstranintViolationException.class)
    public ResponseEntity<ErrorRecordResponse> handleConstranintViolationException(final ConstranintViolationException ex) {
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorRecordResponse);
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<ErrorRecordResponse> handleInvalidCurrentPasswordException(final InvalidCurrentPasswordException ex) {
        var errorRecordResponse =new ErrorRecordResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorRecordResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorRecordResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), ERROR_VALIDATION_FAILED_MESSAGE, errors);
    }
}
