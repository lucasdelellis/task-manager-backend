package com.lucazz82.task.handlers;

public class Violation {
	private String fieldName;
	private String message;

	public Violation(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	@Override
	public String toString() {
		return fieldName + ": " + message;
	}
}
