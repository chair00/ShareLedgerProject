package com.example.demo.entity;

import jakarta.persistence.*;
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

    private String type;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="ledgerId")
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategoryId")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> child = new ArrayList<>();

    @Builder
    public Category(String name, String type, Ledger ledger, Category parent) {
        this.name = name;
        this.type = type;
        this.ledger = ledger;
        this.parent = parent;
    }
}