package com.smartsoft.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
