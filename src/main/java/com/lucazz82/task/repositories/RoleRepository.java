package com.lucazz82.task.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lucazz82.task.enums.Roles;
import com.lucazz82.task.models.RoleModel;

public interface RoleRepository extends CrudRepository<RoleModel, Long> {
	public Optional<RoleModel> findByRole(Roles roles);
	public boolean existsByRole(Roles role);
}
