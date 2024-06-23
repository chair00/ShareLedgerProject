package com.example.demo.dto;

import com.example.demo.entity.Ledger;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LedgerDto {

    private String ledgerName;

    @Builder
    public LedgerDto(Ledger ledger) {
        this.ledgerName = ledger.getLedgerName();
    }

    @Builder
    public Ledger toEntity() {
        return Ledger.builder()
                .ledgerName(ledgerName)
                .build();
    }

}