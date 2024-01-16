package com.usefindar.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException(String message) {
        super(message);
    }

    public CustomAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
