package com.hyh.prettyskin.utils.reflect;


class ReflectException extends RuntimeException {

    ReflectException(String message) {
        super(message);
    }

    ReflectException(String message, Throwable cause) {
        super(message, cause);
    }
}