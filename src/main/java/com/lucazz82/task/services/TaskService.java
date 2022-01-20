package com.lucazz82.task.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucazz82.task.handlers.BadRequestException;
import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.handlers.ServerErrorException;
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
		TaskModel task = null;
		try {
			task = _taskRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Task not found");
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
		
		return task;
	}
	
	public TaskModel newTask(TaskModel task) {
		if(task.getContent() == null || task.getContent().isBlank())
			throw new BadRequestException("Task has missing fields");
		
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
