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
		try {
			return _taskRepository.findAll();
			
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
	}
	
	public TaskModel getTask(Long id) {
		try {
			TaskModel task = _taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
			return task;
			
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
		
	}
	
	public TaskModel newTask(TaskModel task) {
		try {
			_taskRepository.save(task);
			return task;	
			
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
	}
	
	public TaskModel deleteTask(Long id) {
		try {
			TaskModel task = getTask(id);
			_taskRepository.delete(task);
			return task;			
			
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
	}
	
	public TaskModel editTask(Long id, TaskModel editedTask) {
		try {
			TaskModel task = getTask(id);
			task.setContent(editedTask.getContent());
			_taskRepository.save(task);
			return task;	
			
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
	}
}
