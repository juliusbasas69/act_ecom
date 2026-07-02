package com.example.backend.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.auth.dto.RegisterRequest;
import com.example.backend.auth.services.AuthService;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
	public String register(@RequestBody RegisterRequest req) {
		
		try {
			authService.registerUser(req);
		} catch(Exception e) {
			e.printStackTrace();
			
			return "User Failed";
		}
		
		return "User Registered";
	}
}
