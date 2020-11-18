package com.sherlockvarious.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sunchao
 * @created at 2020-11-06-14:30
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException  extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
