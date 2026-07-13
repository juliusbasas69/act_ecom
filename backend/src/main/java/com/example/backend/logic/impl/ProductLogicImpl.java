package com.example.backend.logic.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.dao.ProductDao;
import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.logic.ProductLogic;

@Service
public class ProductLogicImpl implements ProductLogic {

    @Autowired
    private ProductDao productDao;

    @Value("${product.max.display}")
    private String PRODUCT_MAX_DISPLAY;

      @Override
    public void saveProduct(ProductEntity product) {
        
        productDao.save(product);
    }

    @Override
    public Page<ProductEntity> getAllProducts(int page, String search) {

        Pageable pageable = PageRequest.of(page, Integer.parseInt(PRODUCT_MAX_DISPLAY));
        
        return productDao.getAllProducts(pageable, search);
    }

    @Override
    public Optional<ProductEntity> findProductById(int id) {
        
        return productDao.findById(id);
    }

  
    
}
