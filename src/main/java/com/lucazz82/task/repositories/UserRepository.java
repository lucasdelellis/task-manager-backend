package com.lucazz82.task.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.models.TaskModel;
import com.lucazz82.task.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer>{
		public ArrayList<UserModel> findAll();
		public UserModel findByUsername(String username) throws NotFoundException;
}
