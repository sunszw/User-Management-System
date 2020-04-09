package com.ssmsun.management.global.exception;

public class UnConfirmException extends Exception {
    private static final long serialVersionUID = -7086400614560133019L;

    public UnConfirmException() {
        super();
    }

    public UnConfirmException(String message) {
        super(message);
    }

    public UnConfirmException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnConfirmException(Throwable cause) {
        super(cause);
    }

    protected UnConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
