package com.project.cisco.exception.handler;

import com.project.cisco.exception.*;
import com.project.cisco.exception.GeneralException;
import com.project.cisco.exception.NotAllowedLanguageException;
import com.project.cisco.exception.NotFoundException;
import com.project.cisco.exception.UniqueConstraintViolationException;
import com.project.cisco.exception.LengthConstraintViolationException;
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

    @ExceptionHandler({UniqueConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LengthConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleLengthConstraintViolationException(LengthConstraintViolationException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotAllowedLanguageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleNotAllowedLanguageException(NotAllowedLanguageException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LengthConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleLengthConstraintViolationException(LengthConstraintViolationException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidTagsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleInvalidTagsException(InvalidTagsException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotAllowedOriginalMessageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleNotAllowedOriginalMessageException(NotAllowedOriginalMessageException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }


    private ErrorDto buildErrorMessage(GeneralException ex){
        return ErrorDto.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build();
    }
}