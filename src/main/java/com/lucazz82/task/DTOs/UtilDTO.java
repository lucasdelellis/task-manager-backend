package com.lucazz82.task.DTOs;

import org.springframework.stereotype.Component;

import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;

@Component
public class UtilDTO {
	public UserDTO userDTOFromUserModel(UserModel user) {
		UserDTO dto = new UserDTO();
		
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setTasks(user.getTasks());
		
		return dto;
	}
	
	public TaskDTO taskDTOfromTask(TaskModel task) {
		TaskDTO dto = new TaskDTO();
		
		dto.setId(task.getId());
		dto.setContent(task.getContent());
		dto.setUser(userDTOFromUserModel(task.getUser()));
		
		return dto;
	}
}
