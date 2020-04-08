package com.ssmsun.management.global.exception;

public class VerifyNotMatchException extends Exception {
    public VerifyNotMatchException() {
        super();
    }

    public VerifyNotMatchException(String message) {
        super(message);
    }

    public VerifyNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyNotMatchException(Throwable cause) {
        super(cause);
    }

    protected VerifyNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    private static final long serialVersionUID = 1096511632977080250L;
}
