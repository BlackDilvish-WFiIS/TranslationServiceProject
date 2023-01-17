package com.project.cisco.exception;

public class InvalidTagsException extends GeneralException{
    private static final String INVALID_TAGS_EXCEPTION_ERROR_CODE = "400";

    public InvalidTagsException(String msg) {
        super(INVALID_TAGS_EXCEPTION_ERROR_CODE, msg);
    }

    public InvalidTagsException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}
