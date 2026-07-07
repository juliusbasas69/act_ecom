package com.example.backend.dao.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
        name="product_code",
        nullable = false
    )
    private String productCode;

    @Column(
        name = "product_name", 
        nullable = false, 
        length = 150)
    private String productName;

    @Column(
        nullable = false, 
        precision = 10, 
        scale = 2)
    private BigDecimal price;

    @Column(
        name = "quantity",
        nullable = false)
    private Integer quantity;

    @Column(
        length = 500)
    private String description;

    @Column(
        nullable = false)
    private String category;
    
    @Column(
        nullable = false)
    private String status;

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