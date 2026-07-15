package com.example.backend.service.impl;

import static com.example.backend.common.constants.CommonConstant.*;
import static com.example.backend.common.constants.MessageConstant.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.common.util.CipherUtil;
import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.ProductEntity;
import com.example.backend.dao.projection.ProductData;
import com.example.backend.dto.request.ProductRequest;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.PaginationResponse;
import com.example.backend.dto.response.ProductResponse;
import com.example.backend.logic.ProductLogic;
import com.example.backend.service.FileService;
import com.example.backend.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductLogic productLogic;

    @Autowired
    private FileService fileService;

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, String search, String category, String price, String stock) throws Exception {
        
        Page<ProductData> products = productLogic.getAllProducts(page, search, category, price, stock);

        return PageResponse.<ProductResponse>builder()
            .content(
                products.getContent()
                    .stream()
                    .map(product -> {
                        try{
                            return ProductResponse.builder()
                                .encryptedId(CipherUtil.encrypt(String.valueOf(product.id())))
                                .productCode(product.productCode())
                                .productName(product.productName())
                                .description(product.description())
                                .category(product.category())
                                .color(product.color())
                                .price(product.price())
                                .quantity(product.quantity())
                                .status(product.status())
                                .isFeatured(product.isFeatured())
                                .createdAt(product.createdAt())
                                .updatedAt(product.updatedAt())
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
    public void createProduct(ProductRequest request, 
        MultipartFile image
    ) {
        
        ProductEntity newProduct = ProductEntity.builder()
            .productCode(request.productCode())
            .productName(request.productName())
            .description(request.description())
            .price(request.price())
            .quantity(request.quantity())
            .category(request.category())
            .status(request.status())
            .isFeatured(request.isFeatured())
            .createdAt(DateUtil.now())
            .updatedAt(DateUtil.now())
            .isDeleted(IS_NOT_DELETED)
            .build();

        if (image != null && !image.isEmpty()) {

            String extension = image.getOriginalFilename()
                .substring(image.getOriginalFilename().lastIndexOf("."));

            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(FILE_TIME_FORMAT));

            String fileName = newProduct.getProductCode() + "_" + timestamp + extension;

            fileService.saveProductImage(image, fileName);

            newProduct.setImage(fileName);

            productLogic.saveProduct(newProduct);
        }
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
                .isFeatured(productEntity.getIsFeatured())
                .imageName(productEntity.getImage())
                .build();
    }

    @Override
    public void editProduct(String encryptedId, ProductRequest request, MultipartFile image) throws Exception {
        
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
        productEntity.setIsFeatured(request.isFeatured());
        productEntity.setUpdatedAt(DateUtil.now());

        if (image != null && !image.isEmpty()) {

            fileService.deleteProductImage(productEntity.getImage());

            String extension = image.getOriginalFilename()
                .substring(image.getOriginalFilename().lastIndexOf("."));

            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(FILE_TIME_FORMAT));

            String fileName = productEntity.getProductCode() + "_" + timestamp + extension;

            fileService.saveProductImage(image, fileName);

            productEntity.setImage(fileName);
        }

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
        productEntity.setUpdatedAt(DateUtil.now());

        productLogic.saveProduct(productEntity);
    }


}
