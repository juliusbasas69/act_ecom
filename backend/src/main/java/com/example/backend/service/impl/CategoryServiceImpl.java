package com.example.backend.service.impl;

import static com.example.backend.common.constants.CommonConstant.*;
import static com.example.backend.common.constants.MessageConstant.*;

import java.util.Optional;

import javax.management.RuntimeMBeanException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.common.util.CipherUtil;
import com.example.backend.common.util.DateUtil;
import com.example.backend.dao.entity.CategoryEntity;
import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.dto.response.PageResponse;
import com.example.backend.dto.response.PaginationResponse;
import com.example.backend.logic.CategoryLogic;
import com.example.backend.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryLogic categoryLogic;

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, String search, boolean isRetrievingAll) {
        
        Page<CategoryEntity> categories = categoryLogic.getAllCategories(page, search, isRetrievingAll);

        return PageResponse.<CategoryResponse>builder()
            .content(
                categories.getContent()
                    .stream()
                    .map(category -> {
                        try{
                            return CategoryResponse.builder()
                                .encryptedId(CipherUtil.encrypt(String.valueOf(category.getId())))
                                .categoryCode(category.getCategoryCode())
                                .categoryName(category.getCategoryName())
                                .description(category.getDescription())
                                .status(category.getStatus())
                                .color(category.getColor())
                                .icon(category.getIcon())
                                .createdAt(category.getCreatedAt())
                                .updatedAt(category.getUpdatedAt())
                                .build();
                        }catch(Exception e){
                            return null;
                        }
                    }
                ).toList()
            )
            .pagination(PaginationResponse.builder()
                .page(categories.getNumber())
                .totalPages(categories.getTotalPages())
                .totalElements(categories.getTotalElements())
                .hasNext(categories.hasNext())
                .hasPrevious(categories.hasPrevious())
                .build()           
            )   
            .build();
    }

    @Override
    public void createCategory(CategoryRequest request) {
        
        CategoryEntity newCategory = CategoryEntity.builder()
            .categoryCode(request.categoryCode())
            .categoryName(request.categoryName())
            .description(request.description())
            .status(request.status())
            .color(request.color())
            .icon(request.icon())
            .createdAt(DateUtil.now())
            .updatedAt(DateUtil.now())
            .isDeleted(IS_NOT_DELETED)
            .build();

        categoryLogic.saveCategory(newCategory);
    }

    @Override
    public CategoryResponse findCategoryById(String encryptedId) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<CategoryEntity> category = categoryLogic.findCategoryById(id);

        CategoryEntity categoryEntity = category.orElseThrow(() ->
            new RuntimeException(CATEGORY_NOT_FOUND)
        );

        return CategoryResponse.builder()
            .encryptedId(encryptedId)
            .categoryCode(categoryEntity.getCategoryCode())
            .categoryName(categoryEntity.getCategoryName())
            .description(categoryEntity.getDescription())
            .color(categoryEntity.getColor())
            .status(categoryEntity.getStatus())
            .icon(categoryEntity.getIcon())
            .build();
        
    }

    @Override
    public void editCategory(String encryptedId, CategoryRequest request) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<CategoryEntity> category = categoryLogic.findCategoryById(id);

        CategoryEntity categoryEntity = category.orElseThrow(() ->
            new RuntimeException(CATEGORY_NOT_FOUND)
        );

        categoryEntity.setCategoryCode(request.categoryCode());
        categoryEntity.setCategoryName(request.categoryName());
        categoryEntity.setDescription(request.description());
        categoryEntity.setStatus(request.status());
        categoryEntity.setColor(request.color());
        categoryEntity.setIcon(request.icon());
        categoryEntity.setUpdatedAt(DateUtil.now());

        categoryLogic.saveCategory(categoryEntity);
    }

    @Override
    public void deleteCategory(String encryptedId) throws Exception {
        
        int id = Integer.parseInt(CipherUtil.decrypt(encryptedId));

        Optional<CategoryEntity> category = categoryLogic.findCategoryById(id);

        CategoryEntity categoryEntity = category.orElseThrow(() ->
            new RuntimeException(CATEGORY_NOT_FOUND)
        );

        categoryEntity.setIsDeleted(IS_DELETED);
        categoryEntity.setUpdatedAt(DateUtil.now());

        categoryLogic.saveCategory(categoryEntity);
    }
}
