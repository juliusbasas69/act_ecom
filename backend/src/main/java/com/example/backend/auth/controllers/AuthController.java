package com.example.backend.auth.controllers;

import static com.example.backend.common.constants.CommonConstant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.auth.dto.LoginRequest;
import com.example.backend.auth.dto.AuthResponse;
import com.example.backend.auth.services.AuthService;
import com.example.backend.dto.response.ErrorResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            AuthResponse response = authService.login(request);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

        }catch(Exception e){

            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                    e.getMessage()
                ));
        }
    }
}
