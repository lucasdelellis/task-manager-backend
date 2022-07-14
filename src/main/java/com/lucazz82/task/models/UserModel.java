package com.lucazz82.task.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<TaskModel> tasks;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_groups", 
		joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private Set<RoleModel> roles;
}
