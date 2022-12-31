package com.project.cisco.exception;


public class NotFoundException extends GeneralException{
    private static final String NOT_FOUND_EXCEPTION_ERROR_CODE = "404";

    public NotFoundException(String msg) {
        super(NOT_FOUND_EXCEPTION_ERROR_CODE, msg);
    }

    public NotFoundException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}
