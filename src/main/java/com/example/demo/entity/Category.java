package com.example.demo.entity;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="ledgerId", nullable = false)
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategoryId")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> child = new ArrayList<>();

    @Builder
    public Category(String name, CategoryType type, Ledger ledger, Category parent) {
        this.name = name;
        this.type = type;
        this.ledger = ledger;
        this.parent = parent;
    }
}