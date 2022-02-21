package com.lucazz82.task.handlers;

public class NotUniqueUsernameException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotUniqueUsernameException(String message) {
		super(message);
	}
}
