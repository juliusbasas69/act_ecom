package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.ProductResponse;
@Service
public interface ProductService {

    public PageResponse<ProductResponse> getAllProducts(int page, String search) throws Exception;

    public ProductResponse findProductById(String encryptedId) throws Exception;
    
    public void createProduct(ProductRequest request);

    public void editProduct(String encryptedId, ProductRequest request) throws Exception;

    public void deleteProduct(String encryptedId) throws Exception;
}
