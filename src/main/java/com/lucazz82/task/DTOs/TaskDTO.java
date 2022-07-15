package com.lucazz82.task.DTOs;

import lombok.Data;

@Data
public class TaskDTO {
	private Long id;
	private String content;
	private UserDTO user;	
}
