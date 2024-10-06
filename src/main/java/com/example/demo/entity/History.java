package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ledgerId", nullable = false)
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId", nullable = false)
    private Category category;

    @Builder
    public History(String name, Long price, String date, Ledger ledger, Category category) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.ledger = ledger;
        this.category = category;
    }
}
