package com.lucazz82.task.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucazz82.task.enums.Roles;
import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.handlers.NotUniqueUsernameException;
import com.lucazz82.task.handlers.ServerErrorException;
import com.lucazz82.task.models.RoleModel;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.RoleRepository;
import com.lucazz82.task.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private RoleRepository _roleRepository;

	@Autowired
	private PasswordEncoder _passwordEncoder;

	public UserModel getUserByUsername(String username) {
		UserModel user = _userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException(String.format("User %s does not exist", username)));
		return user;
	}

	public UserModel getUserById(Long id) {
		UserModel user = _userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
		return user;
	}

	public UserModel newUser(UserModel user) {
		String username = user.getUsername();
		String password = user.getPassword();

		if (_userRepository.existsByUsername(username)) {
			throw new NotUniqueUsernameException(String.format("Username %s already exists", username));
		}

		user.setPassword(_passwordEncoder.encode(password));
		RoleModel role = _roleRepository.findByRole(Roles.USER)
				.orElseThrow(() -> new ServerErrorException(String.format("Role {} not found", Roles.USER.getName())));
		user.addRole(role);
		_userRepository.save(user);

		return user;
	}

	public UserModel getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) authentication.getPrincipal();
		return getUserByUsername(username);
	}

	public void changePassword(Long id, String password) {
		UserModel user = getUserById(id);
		user.setPassword(_passwordEncoder.encode(password));
		_userRepository.save(user);
	}

	@Transactional
	public void createAdmin(UserModel user) {
		String username = user.getUsername();
		String password = user.getPassword();

		if (_userRepository.existsByUsername(username)) {
			throw new NotUniqueUsernameException(String.format("Username %s already exists", username));
		}

		user.setPassword(_passwordEncoder.encode(password));
		RoleModel role = _roleRepository.findByRole(Roles.USER)
				.orElseThrow(() -> new ServerErrorException(String.format("Role {} not found", Roles.USER.getName())));
		user.addRole(role);
		role = _roleRepository.findByRole(Roles.ADMIN)
				.orElseThrow(() -> new ServerErrorException(String.format("Role {} not found", Roles.ADMIN.getName())));
		user.addRole(role);
		_userRepository.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = _userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		List<RoleModel> roles = user.getRoles();

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name()));
		});
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

	public List<UserModel> getUsers() {
		return _userRepository.findAll();
	}
}
