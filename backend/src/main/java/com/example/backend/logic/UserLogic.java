package com.example.backend.logic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.UserEntity;

@Service
public interface UserLogic {
    
    public void saveUser(UserEntity user);

	public UserEntity findUserByEmail(String email);

    public Page<UserEntity> getAllUsers(int page, String search);

    public Optional<UserEntity> findUserById(int id);
}
