package com.project.cisco.exception;


public class LengthConstraintViolationException extends GeneralException{
    private static final String LENGTH_CONSTRAINT_VIOLATION_EXCEPTION = "400";

    public LengthConstraintViolationException(String msg) {
        super(LENGTH_CONSTRAINT_VIOLATION_EXCEPTION, msg);
    }

    public LengthConstraintViolationException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}

