package com.lucazz82.task.DTOs;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TaskDTO {
	private Long id;
	@NotBlank(message = "Content cannot be blank")
	private String content;
	private UserDTO user;	
}
