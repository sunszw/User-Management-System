package com.ssmsun.management.global.exception;

public class InsertUserException extends Exception {
    private static final long serialVersionUID = -9167282513282486598L;

    public InsertUserException() {
        super();
    }

    public InsertUserException(String message) {
        super(message);
    }

    public InsertUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertUserException(Throwable cause) {
        super(cause);
    }

    protected InsertUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
