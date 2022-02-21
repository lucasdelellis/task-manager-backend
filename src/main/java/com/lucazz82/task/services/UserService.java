package com.lucazz82.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.handlers.NotUniqueUsernameException;
import com.lucazz82.task.handlers.ServerErrorException;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository _userRepository;
	
	public UserModel getUserByUsername(String username) {
		try {
			UserModel user = _userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User does not exist"));
			return user;
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerErrorException("Internal Server Error");
		}
	}
	
	
	public UserModel newUser(UserModel user) {
		try {
			getUserByUsername(user.getUsername());
			throw new NotUniqueUsernameException("Username already exists");
		} catch (NotFoundException e) {			
			_userRepository.save(user);
			return user;
		}
	}
}
