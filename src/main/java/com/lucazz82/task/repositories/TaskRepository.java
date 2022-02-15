package com.lucazz82.task.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.models.TaskModel;

public interface TaskRepository extends CrudRepository<TaskModel, Integer>{
	public ArrayList<TaskModel> findAll();
//	public Optional<TaskModel> findById(Long id);
	public TaskModel findById(Long id) throws NotFoundException;
}
