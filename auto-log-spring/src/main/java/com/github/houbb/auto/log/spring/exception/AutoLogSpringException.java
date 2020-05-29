package com.github.houbb.auto.log.spring.exception;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class AutoLogSpringException extends RuntimeException {

    public AutoLogSpringException() {
    }

    public AutoLogSpringException(String message) {
        super(message);
    }

    public AutoLogSpringException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoLogSpringException(Throwable cause) {
        super(cause);
    }

    public AutoLogSpringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
