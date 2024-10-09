package com.example.demo.entity;

import com.example.demo.dto.HistoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ledgerId", nullable = false)
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId", nullable = false)
    private Category category;

    @Builder
    public History(String name, Long price, LocalDate date, Ledger ledger, Category category) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.ledger = ledger;
        this.category = category;
    }

    public void update(HistoryDTO.Request req) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.category = category;
    }
}
