package com.lucazz82.task.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task")
public class TaskModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Content can't be blank")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private UserModel user;
	
	public TaskModel() {
		super();
	}

	public TaskModel(Long id) {
		super();
		this.id = id;
	}

	public TaskModel(Long id, String content) {
		super();
		this.id = id;
		this.content = content;
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

	public void setUser(UserModel user) {
		this.user = user;
	}
	
}
