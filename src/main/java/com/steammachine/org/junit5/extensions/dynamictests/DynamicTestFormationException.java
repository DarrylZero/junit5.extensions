package com.steammachine.org.junit5.extensions.dynamictests;

/**
 *
 * Исключение правильности построения динамических тестов.
 *
 * @author Vladimir Bogodukhov
 * {@link com.steammachine.org.junit5.extensions.dynamictests.DynamicTestFormationException}
 * com.steammachine.org.junit5.extensions.dynamictests.DynamicTestFormationException
 **/
public class DynamicTestFormationException extends RuntimeException {
    public DynamicTestFormationException() {
    }

    public DynamicTestFormationException(String message) {
        super(message);
    }

    public DynamicTestFormationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicTestFormationException(Throwable cause) {
        super(cause);
    }

    public DynamicTestFormationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
