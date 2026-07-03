package com.example.backend.dto.response;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    
    private String userId;

    private String firstName;

    private String familyName;

    private String email;

    private Timestamp createdAt;

    private String role;
}
