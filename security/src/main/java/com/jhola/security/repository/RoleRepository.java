package com.jhola.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.jhola.security.model.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

	RoleEntity findByName(String name);
	
}
