package com.lucazz82.task.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private List<RoleDTO> roles;
}
