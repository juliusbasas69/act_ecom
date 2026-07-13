package com.example.backend.logic.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.dao.CategoryDao;
import com.example.backend.dao.entity.CategoryEntity;
import com.example.backend.logic.CategoryLogic;

@Service
public class CategoryLogicImpl implements CategoryLogic{

    @Autowired
    private CategoryDao categoryDao;

    @Value("${category.max.display}")
    private String CATEGORY_MAX_DISPLAY;

    @Override
    public void saveCategory(CategoryEntity category) {
        
        categoryDao.save(category);
    }

    @Override
    public Page<CategoryEntity> getAllCategories(int page, String search, boolean isRetrievingAll) {

        Pageable pageable = PageRequest.of(page, Integer.parseInt(CATEGORY_MAX_DISPLAY));

        if(isRetrievingAll){
            pageable = Pageable.unpaged();
        }
        
        return categoryDao.getAllCategories(pageable, search);
    }

    @Override
    public Optional<CategoryEntity> findCategoryById(int id) {
        
        return categoryDao.findById(id);
    }
    
}
