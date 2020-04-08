package com.ssmsun.management.global.exception;

public class SelectUserException extends Exception {
    private static final long serialVersionUID = 665802251743559450L;

    public SelectUserException() {
        super();
    }

    public SelectUserException(String message) {
        super(message);
    }

    public SelectUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectUserException(Throwable cause) {
        super(cause);
    }

    protected SelectUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
