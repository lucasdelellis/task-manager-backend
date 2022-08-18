package com.lucazz82.task.enums;

public enum Roles {
	ADMIN("admin"), USER("user");

	String name;

	private Roles(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
