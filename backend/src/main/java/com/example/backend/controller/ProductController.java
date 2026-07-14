package com.example.backend.controller;

import static com.example.backend.common.constants.MessageConstant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.ErrorResponse;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.ProductResponse;
import com.example.backend.dto.response.SuccessResponse;
import com.example.backend.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String search,
        @RequestParam(defaultValue = "") String category,
        @RequestParam(defaultValue = "") String price,
        @RequestParam(defaultValue = "") String stock
    ) {
        try{

            PageResponse<ProductResponse> response = productService.getAllProducts(page, search, category, price, stock);

            return ResponseEntity.ok(response);

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
        @Validated(CreateValidation.class) @RequestBody ProductRequest request){

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

    @GetMapping("/edit/{encryptedId}")
    public ResponseEntity<?> showEditProduct(@PathVariable("encryptedId") String encryptedId){

        try{

            ProductResponse response = productService.findProductById(encryptedId);

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
    public ResponseEntity<?> editProduct(
        @PathVariable("encryptedId") String encryptedId,
        @Validated(EditValidation.class) @RequestBody ProductRequest request){

        try{

            productService.editProduct(encryptedId, request);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse(PRODUCT_UPDATED_MESSAGE));

        }catch(Exception e){
            e.printStackTrace();;

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
        }
    }

    @PostMapping("/delete/{encryptedId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("encryptedId") String encryptedId){

        try{

            productService.deleteProduct(encryptedId);

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
