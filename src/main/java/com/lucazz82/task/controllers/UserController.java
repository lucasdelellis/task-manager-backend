package com.lucazz82.task.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucazz82.task.DTOs.UserDTO;
import com.lucazz82.task.DTOs.UtilDTO;
import com.lucazz82.task.handlers.ForbiddenException;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.services.UserService;

import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService _userService;

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserInformation(@PathVariable("id") Long id) {
		UserModel user = getValidatedUser(id);
		UserDTO dto = UtilDTO.userDTOFromUserModel(user);

		return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> changePassword(@PathVariable("id") Long id, @Valid @RequestBody Password password) {
		getValidatedUser(id);
		_userService.changePassword(id, password.getPassword());

		return ResponseEntity.ok().build();
	}

	/**
	 * Validate that the user in security context match the id given in path.
	 * 
	 * @param userId
	 * @return
	 */
	private UserModel getValidatedUser(Long userId) {
		UserModel user = _userService.getUserFromSecurityContext();

		if (user.getId() != userId) {
			throw new ForbiddenException("invalid user id");
		}

		return user;
	}

	@Data
	@NoArgsConstructor
	public class Password {
		@NotBlank(message = "password cannot be null")
		String password;
	}
}
