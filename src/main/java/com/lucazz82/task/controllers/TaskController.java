package com.lucazz82.task.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.DTOs.TaskDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private TaskService _taskService;
	
	@Autowired
	private UtilDTO utilDTO;
	
	
	@GetMapping()
	public ResponseEntity<ArrayList<TaskDTO>> getTasks() {
		ArrayList<TaskModel> tasks = _taskService.getTasks();
		ArrayList<TaskDTO> tasksDTO = new ArrayList<>(); 
		tasks.forEach(task -> {
			tasksDTO.add(utilDTO.taskDTOfromTask(task));
		});
		
		return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> getTask(@PathVariable("id") Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) authentication.getPrincipal();
		return new ResponseEntity<>(utilDTO.taskDTOfromTask(_taskService.getTask(id)), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<TaskDTO> newTask(@Valid @RequestBody TaskModel task) {
		return new ResponseEntity<>(utilDTO.taskDTOfromTask(_taskService.newTask(task)), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> deleteTask(@PathVariable Long id) {
		return new ResponseEntity<>(utilDTO.taskDTOfromTask(_taskService.deleteTask(id)), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<TaskDTO> editTask(@PathVariable Long id,@Valid @RequestBody TaskModel task) {
		return new ResponseEntity<>(utilDTO.taskDTOfromTask(_taskService.editTask(id, task)), HttpStatus.OK);
	}
}
