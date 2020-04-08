package com.ssmsun.management.global.exception;

public class UsernameRepeatException extends Exception {
    private static final long serialVersionUID = 7762841179236485515L;

    public UsernameRepeatException() {
        super();
    }

    public UsernameRepeatException(String message) {
        super(message);
    }

    public UsernameRepeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameRepeatException(Throwable cause) {
        super(cause);
    }

    protected UsernameRepeatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
