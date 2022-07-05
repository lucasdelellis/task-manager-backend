package com.lucazz82.task.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "task")
public class TaskModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Content can't be blank")
	private String content;
	
	@ManyToOne(optional=false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private UserModel user;
	
	public TaskModel() {
		super();
	}

	public TaskModel(Long id, @NotBlank(message = "Content can't be blank") String content, UserModel user) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}
	
}
