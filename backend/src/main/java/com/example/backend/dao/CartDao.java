package com.example.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.dao.entity.CartEntity;

public interface CartDao extends JpaRepository<CartEntity, Integer>{
    
}
