package com.project.cisco.exception;

public class NotAllowedOriginalMessageException extends GeneralException{
    private static final String NOT_ALLOWED_ORIGINAL_MESSAGE_EXCEPTION_ERROR_CODE = "400";

    public NotAllowedOriginalMessageException(String msg) {
        super(NOT_ALLOWED_ORIGINAL_MESSAGE_EXCEPTION_ERROR_CODE, msg);
    }

    public NotAllowedOriginalMessageException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}
