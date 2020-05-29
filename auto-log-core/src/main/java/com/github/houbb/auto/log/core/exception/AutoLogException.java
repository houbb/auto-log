package com.github.houbb.auto.log.core.exception;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class AutoLogException extends RuntimeException {

    public AutoLogException() {
    }

    public AutoLogException(String message) {
        super(message);
    }

    public AutoLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoLogException(Throwable cause) {
        super(cause);
    }

    public AutoLogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
