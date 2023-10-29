package com.jhola.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.jhola.security.model.AuthorityEntity;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

	AuthorityEntity findByName(String name);
	
}
