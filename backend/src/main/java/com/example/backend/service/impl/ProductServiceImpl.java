package com.example.backend.service.impl;

import com.example.backend.common.constants.CommonConstant.*;

import static com.example.backend.common.constants.CommonConstant.IS_DELETED;
import static com.example.backend.common.constants.CommonConstant.IS_NOT_DELETED;
import static com.example.backend.common.constants.MessageConstant.PRODUCT_NOT_FOUND;
import static com.example.backend.common.constants.MessageConstant.USER_NOT_FOUND;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.common.util.CipherUtil;
import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.PaginationResponse;
import com.example.backend.dto.response.ProductResponse;
import com.example.backend.logic.ProductLogic;
import com.example.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductLogic productLogic;

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, String search) throws Exception {
        
        Page<ProductEntity> products = productLogic.getAllProducts(page, search);

        return PageResponse.<ProductResponse>builder()
            .content(
                products.getContent()
                    .stream()
                    .map(product -> {
                        try{
                            return ProductResponse.builder()
                                .encryptedId(CipherUtil.encrypt(String.valueOf(product.getId())))
                                .productCode(product.getProductCode())
                                .productName(product.getProductName())
                                .description(product.getDescription())
                                .category(product.getCategory())
                                .price(product.getPrice())
                                .quantity(product.getQuantity())
                                .status(product.getStatus())
                                .createdAt(product.getCreatedAt())
                                .updatedAt(product.getUpdatedAt())
                                .build();

                        }catch(Exception e){
                            return null;
                        }
                    }
                ).toList()
            )
            .pagination(PaginationResponse.builder()
                .page(products.getNumber())
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .hasNext(products.hasNext())
                .hasPrevious(products.hasPrevious())
                .build()           
            )       
            .build();
    }
    

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

    @Override
    public ProductResponse findProductById(String encryptedId) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<ProductEntity> product = productLogic.findProductById(id);

        ProductEntity productEntity = product.orElseThrow(() -> 
            new RuntimeException(PRODUCT_NOT_FOUND)
        );

        return ProductResponse.builder()
                .encryptedId(encryptedId)
                .productCode(productEntity.getProductCode())
                .productName(productEntity.getProductName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .quantity(productEntity.getQuantity())
                .category(productEntity.getCategory())
                .status(productEntity.getStatus())
                .build();
    }

    @Override
    public void editProduct(String encryptedId, ProductRequest request) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<ProductEntity> product = productLogic.findProductById(id);

        ProductEntity productEntity = product.orElseThrow(() -> 
            new RuntimeException(PRODUCT_NOT_FOUND)
        );

        productEntity.setProductCode(request.productCode());
        productEntity.setProductName(request.productName());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setQuantity(request.quantity());
        productEntity.setCategory(request.category());
        productEntity.setStatus(request.status());
        productEntity.setUpdatedAt(DateUtil.now());

        productLogic.saveProduct(productEntity);
    }



    @Override
    public void deleteProduct(String encryptedId) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<ProductEntity> product = productLogic.findProductById(id);

        ProductEntity productEntity = product.orElseThrow(() -> 
            new RuntimeException(PRODUCT_NOT_FOUND)
        );

        productEntity.setIsDeleted(IS_DELETED);

        productLogic.saveProduct(productEntity);
    }


}
