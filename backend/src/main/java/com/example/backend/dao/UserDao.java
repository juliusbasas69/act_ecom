package com.example.backend.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.dao.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>{
    
    public final String FIND_USER_BY_EMAIL = """ 
			SELECT e FROM UserEntity e WHERE e.email = :email
			""";
	
	@Query(FIND_USER_BY_EMAIL)
	public UserEntity findUserByEmail(@Param("email") String email) throws DataAccessException;
}
