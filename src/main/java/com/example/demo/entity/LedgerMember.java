package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여러개의 가계부 멤버가 하나의 Member에 속함.
    @ManyToOne
    @JoinColumn(name = "ledgerId", nullable = false)
    private Ledger ledger;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    private LedgerRole role;
}
