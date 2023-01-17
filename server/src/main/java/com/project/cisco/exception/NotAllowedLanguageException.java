package com.project.cisco.exception;

public class NotAllowedLanguageException extends GeneralException{
    private static final String NOT_ALLOWED_LANGUAGE_EXCEPTION_ERROR_CODE = "400";

    public NotAllowedLanguageException(String msg) {
        super(NOT_ALLOWED_LANGUAGE_EXCEPTION_ERROR_CODE, msg);
    }

    public NotAllowedLanguageException(String errorCode, String msg) {
        super(errorCode, msg);
    }
}
