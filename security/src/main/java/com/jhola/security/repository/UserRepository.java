package com.jhola.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jhola.security.model.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {


    UserEntity findByUsername(String username);
    UserEntity getById(Long id);
}
