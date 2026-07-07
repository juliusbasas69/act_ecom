package com.example.backend.controller;

import static com.example.backend.common.constants.MessageConstant.PRODUCT_CREATED_MESSAGE;
import static com.example.backend.common.constants.MessageConstant.UNEXPECTED_ERROR_MESSAGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.common.constants.MessageConstant;
import com.example.backend.common.validations.CreateValidation;
import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.ErrorResponse;
import com.example.backend.dto.response.SuccessResponse;
import com.example.backend.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
        @Validated(CreateValidation.class) @RequestBody ProductRequest request,
        BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            Map<String, List<String>> errors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                    .add(error.getDefaultMessage());
            }
            
            return ResponseEntity.badRequest().body(errors);
        }

        try{

            productService.createProduct(request);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse(PRODUCT_CREATED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }
}
