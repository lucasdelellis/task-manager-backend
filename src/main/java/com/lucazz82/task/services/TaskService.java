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
	
	// Next step is return error when isPresent is false. Get method throws an exception when fails.
	public TaskModel getTask(Long id) {
		try {
			TaskModel task = _taskRepository.findById(id);
			
			if(task != null) {
				return task;				
			} else {
				throw new NotFoundException("Task not found");
			}
			
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
		
	}
	
	public TaskModel newTask(TaskModel task) {
//		if(task.getContent() == null || task.getContent().isBlank())
//			throw new BadRequestException("Task has missing fields");
		try {
			_taskRepository.save(task);			
		} catch (Exception e) {
			throw e;
		}
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
