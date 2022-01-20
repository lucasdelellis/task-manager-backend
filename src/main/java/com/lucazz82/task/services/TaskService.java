package com.lucazz82.task.services;

import java.util.ArrayList;
import java.util.Optional;

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
	
	// Next step is return error when isPresent is false. Get method throws an exception when fails.
	public TaskModel getTask(Long id) {
		Optional<TaskModel> response = _taskRepository.findById(id);
		TaskModel task = null;
		
		if(response.isPresent()) {
			task = response.get();
		}
		
		return task;
	}
	
	public TaskModel newTask(TaskModel task) {
		_taskRepository.save(task);
		return task;
	}
	
	public TaskModel deleteTask(Long id) {
		TaskModel task = getTask(id);
		_taskRepository.delete(task);
		return task;
	}
	
	public TaskModel editTask(Long id, TaskModel editedTask) {
		TaskModel task = getTask(id);
		task.setContent(editedTask.getContent());
		_taskRepository.save(task);
		return task;
	}
}
