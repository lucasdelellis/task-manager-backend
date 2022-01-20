package com.lucazz82.task.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ArrayList<TaskModel> getTasks() {
		return _taskService.getTasks();
	}
	
	@GetMapping(path = "/{id}")
	public TaskModel getTask(@PathVariable("id") Long id) {
		return _taskService.getTask(id);
	}
	
	@PostMapping()
	public TaskModel newTask(@RequestBody TaskModel task) {
		return _taskService.newTask(task);
	}
	
	@DeleteMapping(path = "/{id}")
	public TaskModel deleteTask(@PathVariable Long id) {
		return _taskService.deleteTask(id);
	}
	
	@PutMapping(path = "/{id}")
	public TaskModel editTask(@PathVariable Long id, @RequestBody TaskModel task) {
		return _taskService.editTask(id, task);
	}
}
