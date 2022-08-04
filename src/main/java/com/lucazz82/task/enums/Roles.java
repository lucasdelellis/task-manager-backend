package com.lucazz82.task.enums;

public enum Roles {
	ROLE_ADMIN("admin"),
	ROLE_USER("user");
	
	String name;
	
	private Roles(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
