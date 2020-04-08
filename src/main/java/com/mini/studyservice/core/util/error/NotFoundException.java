package com.mini.studyservice.core.util.error;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -306314042961146191L;
	
	public NotFoundException() {
		super();
	}
	public NotFoundException(String msg){
		super(msg);
	}
	public NotFoundException(Error error) {
		super(error.getMessage());
	}
}
