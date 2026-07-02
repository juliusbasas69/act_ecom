package com.example.backend.logic;

import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.UserEntity;

@Service
public interface UserLogic {
    
    public void saveUser(UserEntity user);

	public UserEntity findUserByEmail(String email);
}
