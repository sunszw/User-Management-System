package com.ssmsun.management.global.exception;

public class VerifyGetFailException extends Exception {
    private static final long serialVersionUID = -7574647128028862381L;

    public VerifyGetFailException() {
        super();
    }

    public VerifyGetFailException(String message) {
        super(message);
    }

    public VerifyGetFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyGetFailException(Throwable cause) {
        super(cause);
    }

    protected VerifyGetFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
