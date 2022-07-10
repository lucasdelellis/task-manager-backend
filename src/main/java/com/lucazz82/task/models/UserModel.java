package com.lucazz82.task.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private boolean enabled = true;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
	private List<TaskModel> tasks;
	
	public UserModel() {
		super();
	}

	public UserModel(Long id, @NotBlank String username, @NotBlank String password, List<TaskModel> tasks) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tasks = tasks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<TaskModel> getTasks() {
//		return tasks;
		return new ArrayList<TaskModel>();
	}	
}
