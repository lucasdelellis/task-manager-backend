package com.lucazz82.task.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	TaskRepository _taskRepository;
	
	public ArrayList<TaskModel> getTasks() {
		return _taskRepository.findAll();
	}
	
	public TaskModel newTask(TaskModel task) {
		_taskRepository.save(task);
		return task;
	}
}
