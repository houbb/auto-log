package com.github.houbb.auto.log.exception;

/**
 * @author binbin.hou
 * @since 0.0.10
 */
public class AutoLogRuntimeException extends RuntimeException {

    public AutoLogRuntimeException() {
    }

    public AutoLogRuntimeException(String message) {
        super(message);
    }

    public AutoLogRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoLogRuntimeException(Throwable cause) {
        super(cause);
    }

    public AutoLogRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
