package com.lucazz82.task.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.DTOs.SimpleTaskDTO;
import com.lucazz82.task.DTOs.TaskDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.handlers.ForbiddenException;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.TaskService;
import com.lucazz82.task.services.UserService;

@RestController
@RequestMapping("/user/{user_id}/task")
public class TaskController {
	@Autowired
	private TaskService _taskService;
	
	@Autowired
	private UserService _userService;	
	
	/**
	 * Get all tasks for a given user.
	 * @param userId id of the user.
	 * @return all tasks.
	 */
	@GetMapping()
	public ResponseEntity<ArrayList<SimpleTaskDTO>> getTasks(@PathVariable("user_id") Long userId) {
		UserModel user = getValidatedUser(userId);
		
		List<TaskModel> tasks = user.getTasks();
		
		ArrayList<SimpleTaskDTO> tasksDTO = new ArrayList<>(); 
		
		tasks.forEach(task -> {
			tasksDTO.add(UtilDTO.simpleTaskFromTaskModel(task));
		});
		
		return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
	}
	
	/**
	 * Return task of the given id.
	 * @param userId id of the user.
	 * @param id id of the task.
	 * @return task.
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> getTask(@PathVariable("user_id") Long userId, @PathVariable("id") Long id) {
		UserModel user = getValidatedUser(userId);
		
		TaskModel task = _taskService.getTask(user, id);
		TaskDTO dto = UtilDTO.taskDTOFromTaskModel(task);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	
	/**
	 * Create new task.
	 * @param userId
	 * @param taskDTO
	 * @return
	 */
	@PostMapping()
	public ResponseEntity<TaskDTO> newTask(@PathVariable("user_id") Long userId, @Valid @RequestBody TaskDTO taskDTO) {
		UserModel user = getValidatedUser(userId);
		
		TaskModel task = UtilDTO.taskModelFromTaskDTO(taskDTO);
		
		return new ResponseEntity<>(UtilDTO.taskDTOFromTaskModel(_taskService.newTask(user, task)), HttpStatus.CREATED);
	}

	/**
	 * Delete task by id. 
	 * @param userId
	 * @param id
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> deleteTask(@PathVariable("user_id") Long userId, @PathVariable Long id) {
		UserModel user = getValidatedUser(userId);
		
		TaskModel task = _taskService.deleteTask(user, id);
		TaskDTO dto = UtilDTO.taskDTOFromTaskModel(task);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	/**
	 * Edit task by id. 
	 * @param userId
	 * @param id
	 * @param newTask
	 * @return
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> editTask(@PathVariable("user_id") Long userId, @PathVariable Long id, @Valid @RequestBody TaskDTO newTask) {
		UserModel user = getValidatedUser(userId);
		
		TaskModel task = UtilDTO.taskModelFromTaskDTO(newTask);
		task = _taskService.editTask(user, id, task);
		TaskDTO dto = UtilDTO.taskDTOFromTaskModel(task);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * Validate that the user in security context match the id given in path. 
	 * @param userId
	 * @return
	 */
	private UserModel getValidatedUser(Long userId) {
		UserModel user = _userService.getUserFromSecurityContext();
		
		if(user.getId() != userId) {
			throw new ForbiddenException("invalid user id");
		}
		
		return user;
	}
}
