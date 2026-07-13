package com.example.backend.logic;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.backend.dao.entity.CategoryEntity;

@Service
public interface CategoryLogic {
    
    public void saveCategory(CategoryEntity category);

    public Page<CategoryEntity> getAllCategories(int page, String search, boolean isRetrievingAll);

    public Optional<CategoryEntity> findCategoryById(int id);
}
