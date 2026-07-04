package com.example.backend.controller;

import static com.example.backend.common.constants.MessageConstant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.UserCreateRequest;
import com.example.backend.dto.response.ErrorResponse;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.SuccessResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }
    
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String search) {

        try {

            PageResponse<UserResponse> response = userService.getAllUsers(page, search);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request){

        try{

            userService.createUser(request);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse(USER_CREATED_MESSAGE));
            
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }
}
