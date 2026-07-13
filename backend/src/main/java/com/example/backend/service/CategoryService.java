package com.example.backend.service;

import org.springframework.stereotype.Service;

import com.example.backend.dto.request.CategoryRequest;
import com.example.backend.dto.response.CategoryResponse;
import com.example.backend.dto.response.PageResponse;

@Service
public interface CategoryService {

    public PageResponse<CategoryResponse> getAllCategories(int page, String search, boolean isRetrievingAll);
    
    public void createCategory(CategoryRequest request);

    public CategoryResponse findCategoryById(String encryptedId) throws Exception;

    public void editCategory(String encryptedId, CategoryRequest request) throws Exception;

    public void deleteCategory(String encryptedId) throws Exception;
}
