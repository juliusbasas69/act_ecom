package com.example.backend.dao.entity;

import java.math.BigDecimal;
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
@Table(name = "cart_items")
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(
        name = "cart_id",
        nullable = false
    )
    private Integer cartId;

    @Column(
        name = "product_id",
        nullable = false
    )
    private Integer productId;

    @Column(
        name = "quantity",
        nullable = false
    )
    private Integer quantity;

    @Column(
        name = "price",
        nullable = false
    )
    private BigDecimal price;

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
