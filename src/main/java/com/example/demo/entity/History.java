package com.example.demo.entity;

import com.example.demo.dto.HistoryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime date;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ledger", nullable = false)
    private Ledger ledger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category", nullable = false)
    private Category category;

    @Builder
    public History(String name, Long price, LocalDateTime date, Ledger ledger, Category category, String memo) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.ledger = ledger;
        this.category = category;
        this.memo = memo;
    }

}
