package com.lucazz82.task.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping()
	public TaskModel newTask(@RequestBody TaskModel task) {
		return _taskService.newTask(task);
	}
}
