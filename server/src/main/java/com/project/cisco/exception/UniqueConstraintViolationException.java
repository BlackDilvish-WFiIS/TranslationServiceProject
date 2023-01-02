package com.project.cisco.exception;

public class UniqueConstraintViolationException extends GeneralException{
    private static final String UNIQUE_CONSTRAINT_VIOLATION_EXCEPTION = "400";

    public UniqueConstraintViolationException(String msg) {
        super(UNIQUE_CONSTRAINT_VIOLATION_EXCEPTION, msg);
    }

    public UniqueConstraintViolationException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}

