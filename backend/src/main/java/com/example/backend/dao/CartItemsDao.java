package com.example.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dao.entity.CartItemsEntity;

public interface CartItemsDao extends JpaRepository<CartItemsEntity, Integer> {
    
}
