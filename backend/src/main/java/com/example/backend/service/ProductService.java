package com.example.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.ProductResponse;

@Service
public interface ProductService {

    public PageResponse<ProductResponse> getAllProducts(int page, String search, String category, String price, String stock) throws Exception;

    public ProductResponse findProductById(String encryptedId) throws Exception;
    
    public void createProduct(ProductRequest request, MultipartFile image);

    public void editProduct(String encryptedId, ProductRequest request, MultipartFile image) throws Exception;

    public void deleteProduct(String encryptedId) throws Exception;

    public List<ProductResponse> getFeaturedProducts();

}
