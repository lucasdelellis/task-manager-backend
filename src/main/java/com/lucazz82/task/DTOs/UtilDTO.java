package com.lucazz82.task.DTOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.lucazz82.task.models.RoleModel;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;

@Component
public class UtilDTO {
	public static UserDTO userDTOFromUserModel(UserModel user) {
		UserDTO dto = new UserDTO();
		
		BeanUtils.copyProperties(user, dto);
		
		List<RoleDTO> rolesDTO = new ArrayList<>();
		
		for(RoleModel role : user.getRoles()) {
			rolesDTO.add(roleDTOFromRoleModel(role));
		}
	
		dto.setRoles(rolesDTO);
		
		return dto;
	}
	
	public static UserModel userModelFromUserDTO(UserDTO dto) {
		UserModel user = new UserModel();
		
		BeanUtils.copyProperties(dto, user);
		
		List<RoleModel> roles = new ArrayList<>();
		
		for(RoleDTO roleDTO : dto.getRoles()) {
			roles.add(roleModelFromRoleDTO(roleDTO));
		}
		
		user.setRoles(roles);
		
		return user;
	}
	
	public static TaskDTO taskDTOFromTaskModel(TaskModel task) {
		TaskDTO dto = new TaskDTO();
		
		BeanUtils.copyProperties(task, dto);
		
		dto.setUser(userDTOFromUserModel(task.getUser()));
		
		return dto;
	}
	
	public static TaskModel taskModelFromTaskDTO(TaskDTO dto) {
		TaskModel task = new TaskModel();
		
		BeanUtils.copyProperties(dto, task);
				
		return task;
	}
	
	public static RoleDTO roleDTOFromRoleModel(RoleModel role) {
		RoleDTO dto = new RoleDTO();
		
		BeanUtils.copyProperties(role, dto);
		
		return dto;
	}
	
	public static RoleModel roleModelFromRoleDTO(RoleDTO dto) {
		RoleModel role = new RoleModel();
		
		BeanUtils.copyProperties(dto, role);
		
		return role;
	}
	
	public static SimpleTaskDTO simpleTaskFromTaskModel(TaskModel task) {
		SimpleTaskDTO dto = new SimpleTaskDTO();
		
		BeanUtils.copyProperties(task, dto);
		
		return dto;
	}
	
	public static UserModel userModelFromRegisterDTO(RegisterDTO register) {
		UserModel user = new UserModel();
		
		BeanUtils.copyProperties(register, user);
		
		return user;
	}
}
