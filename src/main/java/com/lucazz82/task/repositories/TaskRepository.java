package com.lucazz82.task.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lucazz82.task.models.TaskModel;

public interface TaskRepository extends CrudRepository<TaskModel, Long>{
	public ArrayList<TaskModel> findAll();
	public Optional<TaskModel> findById(Long id);
}
