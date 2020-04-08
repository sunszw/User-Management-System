package com.ssmsun.management.global.exception;

public class DeleteUserException extends Exception {
    private static final long serialVersionUID = 6741633654293933754L;

    public DeleteUserException() {
        super();
    }

    public DeleteUserException(String message) {
        super(message);
    }

    public DeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteUserException(Throwable cause) {
        super(cause);
    }

    protected DeleteUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
