package com.lucazz82.task.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.lucazz82.task.enums.Roles;

@Entity
@Table(name = "role")
public class RoleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Roles role;
	
	@OneToOne
	private UserModel user;
}
