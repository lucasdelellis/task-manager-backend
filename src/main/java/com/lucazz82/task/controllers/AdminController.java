package com.lucazz82.task.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.DTOs.UserDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService _userService;
	
	@GetMapping()
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserModel> users = _userService.getUsers();
		List<UserDTO> dtos = new ArrayList<>();
		users.forEach(user -> {
			dtos.add(UtilDTO.userDTOFromUserModel(user));
		});
		
		return new ResponseEntity<List<UserDTO>>(dtos, HttpStatus.OK);
	}
}
