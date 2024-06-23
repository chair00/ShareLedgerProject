package com.example.demo.entity;

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
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String kind;
    @ManyToOne
    @JoinColumn(name="ledgerId")
    private Ledger ledger;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parentCategoryId")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Category parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Category> child = new ArrayList<>();

}