package com.project.cisco.exception.handler;

import com.project.cisco.exception.GeneralException;
import com.project.cisco.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.NOT_FOUND);
    }

    private ErrorDto buildErrorMessage(GeneralException ex){
        return ErrorDto.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build();
    }
}