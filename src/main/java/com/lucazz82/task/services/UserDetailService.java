package com.lucazz82.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucazz82.task.repositories.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	UserRepository _userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = _userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return user;
	}

}
