package com.app.ws.io.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.ws.io.entity.UserEntity;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
