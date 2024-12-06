package com.example.demo.dto;

import com.example.demo.entity.Ledger;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor

@Schema(description = "가계부 응답/요청 DTO")
public class LedgerDTO {

    @Schema(description = "가계부 이름")
    private String ledgerName;

    @Builder
    public LedgerDTO(Ledger ledger) {
        this.ledgerName = ledger.getName();
    }

    @Builder
    public Ledger toEntity() {
        return Ledger.builder()
                .name(ledgerName)
                .build();
    }

}