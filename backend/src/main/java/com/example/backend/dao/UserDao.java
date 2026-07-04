package com.example.backend.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.dao.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer>{
    
    public final String FIND_USER_BY_EMAIL = """ 
			SELECT e FROM UserEntity e WHERE e.email = :email AND e.isDeleted = false
			""";
	
	@Query(FIND_USER_BY_EMAIL)
	public UserEntity findUserByEmail(@Param("email") String email) throws DataAccessException;

	public final String GET_ALL_USERS = """
			SELECT e 
			FROM UserEntity e 
			WHERE e.isDeleted = false
			AND (
				(:search IS NOT NULL AND :search <> '' AND (
					LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR
					LOWER(e.familyName) LIKE LOWER(CONCAT('%', :search, '%')) OR
					LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')) 
				))
				OR (:search IS NULL OR :search = '')
			)
			""";
	
	@Query(GET_ALL_USERS)
	public Page<UserEntity> getAllUsers(Pageable pageable, @Param("search") String search) throws DataAccessException;
}
