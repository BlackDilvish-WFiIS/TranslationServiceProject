package com.project.cisco.exception;

public class GeneralException extends RuntimeException {
    protected String errorCode;

    public GeneralException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
