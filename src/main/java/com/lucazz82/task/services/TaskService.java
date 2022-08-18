package com.lucazz82.task.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucazz82.task.handlers.ForbiddenException;
import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository _taskRepository;

	public ArrayList<TaskModel> getTasks() {
		return _taskRepository.findAll();
	}

	public TaskModel getTask(UserModel user, Long id) {
		TaskModel task = _taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));

		if (task.getUser() != user) {
			throw new ForbiddenException("access denied to task with id " + id);
		}

		return task;
	}

	public TaskModel newTask(UserModel user, TaskModel task) {
		task.setUser(user);
		_taskRepository.save(task);
		return task;
	}

	public TaskModel deleteTask(UserModel user, Long id) {
		TaskModel task = getTask(user, id);
		_taskRepository.delete(task);
		return task;
	}

	public TaskModel editTask(UserModel user, Long id, TaskModel editedTask) {
		TaskModel task = getTask(user, id);
		task.setContent(editedTask.getContent());
		_taskRepository.save(task);
		return task;
	}
}
