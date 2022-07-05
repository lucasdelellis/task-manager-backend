package com.lucazz82.task.DTOs;

import java.util.List;

import com.lucazz82.task.models.TaskModel;

public class UserDTO {
	private Long id;
	private String username;
	private List<TaskModel> tasks;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String username, List<TaskModel> tasks) {
		super();
		this.id = id;
		this.username = username;
		this.tasks = tasks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<TaskModel> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskModel> tasks) {
		this.tasks = tasks;
	}
	
	
}
