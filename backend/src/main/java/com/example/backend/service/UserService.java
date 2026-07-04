package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.UserRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.UserResponse;

@Service
public interface UserService {
    
    public PageResponse<UserResponse> getAllUsers(int page, String search) throws Exception;

    public void createUser(UserRequest request);

    public UserResponse findUserById(String encryptedId)  throws Exception;

    public void editUser(String encryptedId, UserRequest request)  throws Exception;
}
