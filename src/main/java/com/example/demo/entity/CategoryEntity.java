package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}