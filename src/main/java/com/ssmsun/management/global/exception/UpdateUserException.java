package com.ssmsun.management.global.exception;

public class UpdateUserException extends Exception {
    private static final long serialVersionUID = 8237862717444598892L;

    public UpdateUserException() {
        super();
    }

    public UpdateUserException(String message) {
        super(message);
    }

    public UpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateUserException(Throwable cause) {
        super(cause);
    }

    protected UpdateUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
