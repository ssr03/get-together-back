package com.mini.studyservice.core.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class CustomFileNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 6738738808772199984L;

    public CustomFileNotFoundException(String msg) {
        super(msg);
    }

    public CustomFileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
