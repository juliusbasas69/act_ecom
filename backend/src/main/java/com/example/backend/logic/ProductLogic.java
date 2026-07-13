package com.example.backend.logic;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.ProductEntity;

@Service
public interface ProductLogic {

    public void saveProduct(ProductEntity product);
    
    public Page<ProductEntity> getAllProducts(int page, String search);

    public Optional<ProductEntity> findProductById(int id);
}
