package com.smartsoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class LicencaInvalidaException extends RuntimeException {

    public LicencaInvalidaException(String message) {
        super(message);
    }
}
