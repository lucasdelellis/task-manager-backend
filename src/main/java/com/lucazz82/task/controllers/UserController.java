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

import com.lucazz82.task.DTOs.UserDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService _userService;
	
	@Autowired
	private UtilDTO utilDTO;
	
	@GetMapping(path = "/{username}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
		return new ResponseEntity<>(utilDTO.userDTOFromUserModel(_userService.getUserByUsername(username)), HttpStatus.OK);
	}
	
	@GetMapping(path = "/id/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return new ResponseEntity<>(utilDTO.userDTOFromUserModel(_userService.getUserById(id)), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<UserDTO> newUser(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<>(utilDTO.userDTOFromUserModel(_userService.newUser(user)), HttpStatus.CREATED);
	}
}
