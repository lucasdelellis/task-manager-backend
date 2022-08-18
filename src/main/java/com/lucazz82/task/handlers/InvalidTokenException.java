package com.lucazz82.task.handlers;

public class InvalidTokenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	
	public InvalidTokenException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	

	
}
