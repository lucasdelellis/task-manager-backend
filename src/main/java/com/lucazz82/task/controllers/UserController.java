package com.lucazz82.task.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService _userService;
	
	@GetMapping(path = "/{username}")
	public ResponseEntity<UserModel> getUserByUsername(@PathVariable String username) {
		return new ResponseEntity<>(_userService.getUserByUsername(username), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<UserModel> newUser(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<>(_userService.newUser(user), HttpStatus.CREATED);
	}
}
