package com.lucazz82.task.DTOs;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private Set<RoleDTO> roles;
}
