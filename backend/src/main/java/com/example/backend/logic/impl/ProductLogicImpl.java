package com.example.backend.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.dao.ProductDao;
import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.logic.ProductLogic;

@Service
public class ProductLogicImpl implements ProductLogic {

    @Autowired
    private ProductDao productDao;

      @Override
    public void saveProduct(ProductEntity product) {
        
        productDao.save(product);
    }

    @Override
    public Page<ProductEntity> getAllProducts(int page, String search) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

  
    
}
