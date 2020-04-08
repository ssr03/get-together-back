package com.mini.studyservice.core.util.error;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Error {
	private int status;
    private String message;

    public Error(String message) {
    	this.message = message;
    }
    public Error(HttpStatus code, String message) {
        this.status = code.value();
        this.message = message;
        
    }
}
