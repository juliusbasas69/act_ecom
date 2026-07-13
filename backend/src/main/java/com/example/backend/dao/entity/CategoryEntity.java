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
@Table(name = "category")
@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(
        name = "category_code",
        nullable = false,
        length = 32)
    private String categoryCode;

    @Column(
        name = "category_name",
        nullable = false,
        length = 32)
    private String categoryName;

    @Column(
        name = "description",
        length = 255)
    private String description;

    @Column(
        nullable = false)
    private String status;

    @Column(
        name = "color",
        nullable = false)
    private String color;

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
