package com.lucazz82.task;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucazz82.task.enums.Roles;
import com.lucazz82.task.models.RoleModel;
import com.lucazz82.task.models.UserModel;
import com.lucazz82.task.repositories.RoleRepository;

@SpringBootApplication
public class TaskApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args -> {
			for(Roles role : Roles.values()) {
				if(!roleRepository.existsByRole(role))
					roleRepository.save(new RoleModel(null, role, role.getName(), new ArrayList<UserModel>()));
			}
		};
	}
}
