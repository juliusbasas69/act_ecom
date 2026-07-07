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
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(
        name = "first_name",
        nullable = false
    )
    private String firstName;

    @Column(
        name = "family_name",
        nullable = false)
    private String familyName;

    @Column(
        name = "email", 
        nullable = false)
    private String email;

    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    @Column(
        name = "role",
        nullable = false)
    private String role;

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
    private boolean isDeleted;
}