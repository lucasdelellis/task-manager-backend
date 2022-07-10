package com.lucazz82.task.services;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.lucazz82.task.handlers.BadRequestException;
import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.handlers.ServerErrorException;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.TaskRepository;
import com.lucazz82.task.repositories.UserRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository _taskRepository;
	@Autowired
	private UserRepository _userRepository;
	
	public ArrayList<TaskModel> getTasks() {
		return _taskRepository.findAll();
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
		UserModel user = _userRepository.findById(task.getUser().getId()).orElseThrow(() -> new NotFoundException("User not found"));
		task.setUser(user);
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
