package com.example.backend.service.impl;

import com.example.backend.common.constants.CommonConstant.*;

import static com.example.backend.common.constants.CommonConstant.IS_NOT_DELETED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.dto.request.ProductRequest;
import com.example.backend.logic.ProductLogic;
import com.example.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductLogic productLogic;

    @Override
    public void createProduct(ProductRequest request) {
        
        ProductEntity newProduct = ProductEntity.builder()
            .productCode(request.productCode())
            .productName(request.productName())
            .description(request.description())
            .price(request.price())
            .quantity(request.quantity())
            .category(request.category())
            .status(request.status())
            .createdAt(DateUtil.now())
            .updatedAt(DateUtil.now())
            .isDeleted(IS_NOT_DELETED)
            .build();

        productLogic.saveProduct(newProduct);
    }
    
}
