package com.example.backend.controller;

import static com.example.backend.common.constants.MessageConstant.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.constants.MessageConstant;
import com.example.backend.common.validations.CreateValidation;
import com.example.backend.common.validations.EditValidation;
import com.example.backend.dto.request.UserRequest;
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
    public ResponseEntity<?> createUser(
        @Validated(CreateValidation.class) @RequestBody UserRequest request, 
        BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()) {

            Map<String, List<String>> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
            }
            
            //This is to check password and confirm password temporarily
            if (!request.password().equals(request.confirmPassword())) {
                errors.computeIfAbsent("password", key -> new ArrayList<>())
                    .add(MessageConstant.PASSWORDS_DO_NOT_MATCH);
            }

            return ResponseEntity.badRequest().body(errors);
        }

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

    @GetMapping("/edit/{encryptedId}")
    public ResponseEntity<?> showEditUser(@PathVariable("encryptedId") String encryptedId){

        try{

            UserResponse response = userService.findUserById(encryptedId);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }

    @PostMapping("/edit/{encryptedId}")
    public ResponseEntity<?> editUser(
        @PathVariable("encryptedId") String encryptedId,
        @Validated(EditValidation.class) @RequestBody UserRequest request,
        BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()) {

            Map<String, List<String>> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
            }
            
            //This is to check password and confirm password temporarily
            if (request.password() != null || request.confirmPassword() != null) {
                if (!Objects.equals(request.password(), request.confirmPassword())) {
                    errors.computeIfAbsent("password", key -> new ArrayList<>())
                        .add(MessageConstant.PASSWORDS_DO_NOT_MATCH);
                }
            }

            return ResponseEntity.badRequest().body(errors);
        }

        try{

            userService.editUser(encryptedId, request);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(USER_UPDATED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }

    @PostMapping("/delete/{encryptedId}")
    public ResponseEntity<?> deleteUser(@PathVariable("encryptedId") String encryptedId){

        try{

            userService.deleteUser(encryptedId);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(USER_DELETED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }
}
