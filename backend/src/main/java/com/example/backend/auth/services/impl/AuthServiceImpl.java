package com.example.backend.auth.services.impl;

import static com.example.backend.common.constants.CommonConstant.*;
import static com.example.backend.common.constants.MessageConstant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.auth.dto.RegisterRequest;
import com.example.backend.auth.dto.AuthResponse;
import com.example.backend.auth.services.AuthService;
import com.example.backend.auth.services.JwtService;
import com.example.backend.common.exceptions.AuthException;
import com.example.backend.common.util.CipherUtil;
import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.UserEntity;
import com.example.backend.logic.UserLogic;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        
        UserEntity newUser = UserEntity.builder()
            .firstName(request.getFirstName())
            .familyName(request.getFamilyName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(USER_ROLE)
            .createdAt(DateUtil.now())
            .updatedAt(DateUtil.now())
            .isDeleted(IS_DELETED)
            .build();

        userLogic.saveUser(newUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) throws Exception {
        
        UserEntity user = userLogic.findUserByEmail(request.getEmail());

	    if (user == null) {
	        throw new AuthException(INVALID_CREDENTIALS);
	    }

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new AuthException(INVALID_CREDENTIALS);
	    }
	    
	    String token = jwtService.generateToken(user);
	    
	    return new AuthResponse(CipherUtil.encrypt(String.valueOf(user.getId())),
	    		user.getEmail(), 
	    		user.getRole(), 
	    		token);
      
    }
    
}
