package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.ProductRequest;

@Service
public interface ProductService {
    
    public void createProduct(ProductRequest request);
}
