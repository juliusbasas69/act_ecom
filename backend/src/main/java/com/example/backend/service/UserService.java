package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.UserCreateRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.UserResponse;

@Service
public interface UserService {
    
    public PageResponse<UserResponse> getAllUsers(int page, String search) throws Exception;

    public void createUser(UserCreateRequest request);
}
