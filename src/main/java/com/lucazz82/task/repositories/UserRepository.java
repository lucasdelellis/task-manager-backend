package com.lucazz82.task.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lucazz82.task.models.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Long> {
	public ArrayList<UserModel> findAll();

	public Optional<UserModel> findByUsername(String username);

	public boolean existsByUsername(String username);
}
