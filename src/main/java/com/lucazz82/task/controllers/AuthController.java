package com.lucazz82.task.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.DTOs.RegisterDTO;
import com.lucazz82.task.DTOs.UserDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService _userService;

	@PostMapping(path = "/register")
	public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO form) {
		UserModel user = UtilDTO.userModelFromRegisterDTO(form);
		user = _userService.newUser(user);
		UserDTO dto = UtilDTO.userDTOFromUserModel(user);
		return new ResponseEntity<UserDTO>(dto, HttpStatus.CREATED);
	}
}
