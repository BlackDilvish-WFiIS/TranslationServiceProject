package com.project.cisco.exception;

public class EmptyContentException extends GeneralException{
    private static final String EMPTY_CONTENT_EXCEPTION_ERROR_CODE = "400";

    public EmptyContentException(String msg) {
        super(EMPTY_CONTENT_EXCEPTION_ERROR_CODE, msg);
    }

    public EmptyContentException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}
