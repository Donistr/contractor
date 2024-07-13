package org.example.contractor.controller;

import org.example.contractor.messages.ErrorObject;
import org.example.contractor.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorObject> handle(BaseException exception) {
        return new ResponseEntity<>(new ErrorObject(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
