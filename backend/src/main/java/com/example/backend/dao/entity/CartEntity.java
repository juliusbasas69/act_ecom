package com.example.backend.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class CartEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(
        name = "user_id",
        nullable = false
    )
    private Integer userId;

    @Column(
        name = "created_at", 
        nullable = false, 
        updatable = false)
    private Timestamp createdAt;

    @Column(
        name = "updated_at",
        nullable = false)
    private Timestamp updatedAt;

    @Column(
        name = "is_deleted",
        nullable = false)
    private Boolean isDeleted;


}
