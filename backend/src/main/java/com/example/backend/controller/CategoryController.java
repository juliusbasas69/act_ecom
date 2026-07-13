package com.example.backend.controller;

import static com.example.backend.common.constants.MessageConstant.CATEGORY_CREATED_MESSAGE;
import static com.example.backend.common.constants.MessageConstant.CATEGORY_DELETED_MESSAGE;
import static com.example.backend.common.constants.MessageConstant.CATEGORY_UPDATED_MESSAGE;
import static com.example.backend.common.constants.MessageConstant.PRODUCT_CREATED_MESSAGE;
import static com.example.backend.common.constants.MessageConstant.UNEXPECTED_ERROR_MESSAGE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.backend.common.validations.CreateValidation;
import com.example.backend.common.validations.EditValidation;
import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.dto.response.ErrorResponse;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.SuccessResponse;
import com.example.backend.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String search
    ){
            
        try{

            PageResponse<CategoryResponse> response = categoryService.getAllCategories(page, search);

            return ResponseEntity.ok(response);

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(
        @Validated(CreateValidation.class) @RequestBody CategoryRequest request
    ){

        try{

            categoryService.createCategory(request);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse(CATEGORY_CREATED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }

    @GetMapping("/edit/{encryptedId}")
    public ResponseEntity<?> getCategoryById(
        @PathVariable("encryptedId") String encryptedId
    ){

        try{

            CategoryResponse response = categoryService.findCategoryById(encryptedId);

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

    @PostMapping("/create/{encryptedId}")
    public ResponseEntity<?> createCategory(
        @PathVariable("encryptedId") String encryptedId,
        @Validated(EditValidation.class) @RequestBody CategoryRequest request

    ){
        try{

            categoryService.editCategory(encryptedId, request);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(CATEGORY_UPDATED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }

    @PostMapping("/edit/{encryptedId}")
    public ResponseEntity<?> editCategory(
        @PathVariable("encryptedId") String encryptedId
    ){

        try{

            categoryService.deleteCategory(encryptedId);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(CATEGORY_DELETED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }
}
