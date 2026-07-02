package com.example.backend.auth.services;

import org.springframework.stereotype.Service;

import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.auth.dto.RegisterRequest;
import com.example.backend.auth.dto.AuthResponse;

@Service
public interface AuthService {
    
    public void registerUser(RegisterRequest request);

	public AuthResponse login(LoginRequest request) throws Exception ;
}
