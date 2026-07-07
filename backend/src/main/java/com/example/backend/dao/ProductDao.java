package com.example.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dao.entity.ProductEntity;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {
    
}
