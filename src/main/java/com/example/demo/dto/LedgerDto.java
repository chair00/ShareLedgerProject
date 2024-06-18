package com.example.demo.dto;

import com.example.demo.entity.LedgerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LedgerDto {

    private String ledgerName;

    @Builder
    public LedgerDto(LedgerEntity ledgerEntity) {
        this.ledgerName = ledgerEntity.getLedgerName();
    }

    @Builder
    public LedgerEntity toEntity() {
        return LedgerEntity.builder()
                .ledgerName(ledgerName)
                .build();
    }

}