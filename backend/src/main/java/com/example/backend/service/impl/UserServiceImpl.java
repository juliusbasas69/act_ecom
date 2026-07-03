package com.example.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.common.util.CipherUtil;
import com.example.backend.dao.entity.UserEntity;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.PaginationResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.logic.UserLogic;
import com.example.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserLogic userLogic;

    @Override
    public PageResponse<UserResponse> getAllUsers() throws Exception{
        
        Page<UserEntity> users = userLogic.getAllUsers(0);

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
    
}
