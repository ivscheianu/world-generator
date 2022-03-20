package com.ivscheianu;

public class ValidationMessage extends RuntimeException {
    public ValidationMessage(final String message) {
        super(message);
    }

    public ValidationMessage(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationMessage(final Throwable cause) {
        super(cause);
    }

    public ValidationMessage(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
