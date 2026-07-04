package com.example.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.common.util.CipherUtil;
import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.UserEntity;
import com.example.backend.dto.request.UserCreateRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.PaginationResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.logic.UserLogic;
import com.example.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, String search) throws Exception{
        
        Page<UserEntity> users = userLogic.getAllUsers(page, search);

        return PageResponse.<UserResponse>builder()
            .content(
                users.getContent()
                    .stream()
                    .map(user -> {
                        try {
                            return UserResponse.builder()
                                .userId(CipherUtil.encrypt(String.valueOf(user.getId())))
                                .firstName(user.getFirstName())
                                .familyName(user.getFamilyName())
                                .email(user.getEmail())
                                .createdAt(user.getCreatedAt())
                                .role(user.getRole())
                                .build();
                        } catch (Exception e) {
                            return null;
                        }
                    }    
                ).toList()
            )
            .pagination(PaginationResponse.builder()
                .page(users.getNumber())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .build()           
            )   
            .build();
    }

    @Override
    public void createUser(UserCreateRequest request) {
        
        UserEntity newUser = UserEntity.builder()
            .firstName(request.firstName())
            .familyName(request.familyName())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .createdAt(DateUtil.now())
            .updatedAt(DateUtil.now())
            .role(request.role())
            .build();

        userLogic.saveUser(newUser);
    }
    
}
