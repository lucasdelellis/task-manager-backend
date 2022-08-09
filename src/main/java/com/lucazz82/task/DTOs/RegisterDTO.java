package com.lucazz82.task.DTOs;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterDTO {
	@NotBlank(message = "username cannot be blank")
	private String username;
	@NotBlank(message = "password cannot be blank")
	private String password;
}
