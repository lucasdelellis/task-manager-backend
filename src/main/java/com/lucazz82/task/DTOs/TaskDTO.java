package com.lucazz82.task.DTOs;

public class TaskDTO {
	private Long id;
	private String content;
	private UserDTO user;
	
	public TaskDTO() {
	}
	
	public TaskDTO(Long id, String content, UserDTO user) {
		this.id = id;
		this.content = content;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	
}
