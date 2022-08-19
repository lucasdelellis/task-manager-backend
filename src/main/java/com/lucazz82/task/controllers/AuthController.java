package com.lucazz82.task.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucazz82.task.DTOs.RegisterDTO;
import com.lucazz82.task.DTOs.UserDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;
import com.lucazz82.task.services.UtilService;

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
	
	@GetMapping(path = "/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		DecodedJWT decodedJWT = UtilService.getJWTFromHeader(authorizationHeader);
		String username = decodedJWT.getSubject(); // Obtain username
		
		UserModel user = _userService.getUserByUsername(username);

		List<String> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add("ROLE_" + role.getRole().name()));
		
		String access_token = UtilService.getAccessToken(user.getUsername(), authorities, request.getRequestURL().toString());

		String refresh_token = UtilService.getRefreshToken(user.getUsername(), request.getRequestURL().toString());

		HashMap<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(response.getOutputStream(), tokens);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
