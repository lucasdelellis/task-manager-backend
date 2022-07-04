package com.lucazz82.task.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucazz82.task.handlers.NotFoundException;
import com.lucazz82.task.handlers.NotUniqueUsernameException;
import com.lucazz82.task.handlers.ServerErrorException;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository _userRepository;
	@Autowired
	private PasswordEncoder _passwordEncoder;
	
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
			user.setPassword(_passwordEncoder.encode(user.getPassword()));
			_userRepository.save(user);
			return user;
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = _userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
}
