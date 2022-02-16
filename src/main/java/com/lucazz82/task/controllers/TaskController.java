package com.lucazz82.task.controllers;

import java.util.ArrayList;

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

import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	TaskService _taskService;
	
	
	@GetMapping()
	public ResponseEntity<ArrayList<TaskModel>> getTasks() {
		return new ResponseEntity<>(_taskService.getTasks(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<TaskModel> getTask(@PathVariable("id") Long id) {
		return new ResponseEntity<>(_taskService.getTask(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<TaskModel> newTask(@Valid @RequestBody TaskModel task) {
		return new ResponseEntity<>(_taskService.newTask(task), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<TaskModel> deleteTask(@PathVariable Long id) {
		return new ResponseEntity<>(_taskService.deleteTask(id), HttpStatus.OK);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<TaskModel> editTask(@PathVariable Long id, @RequestBody TaskModel task) {
		return new ResponseEntity<>(_taskService.editTask(id, task), HttpStatus.OK);
	}
}
