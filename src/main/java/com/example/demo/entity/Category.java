package com.example.demo.entity;

import com.example.demo.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> child = new ArrayList<>();

    private Integer level;

    @Builder
    public Category(String name, String type, Ledger ledger, Category parent, Integer level) {
        this.name = name;
        this.type = type;
        this.ledger = ledger;
        this.parent = parent;
        this.level = level;
    }
}