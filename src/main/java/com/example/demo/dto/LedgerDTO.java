package com.example.demo.dto;

import com.example.demo.entity.Ledger;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class LedgerDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "가계부 생성/수정 요청 DTO")
    public static class RequestDTO {

        @Schema(description = "가계부 이름")
        private String ledgerName;

        public RequestDTO(String ledgerName) {
            this.ledgerName = ledgerName;
        }
    }

    @Getter
    @Setter
    @Schema(description = "가계부 정보 조회 DTO")
    public static class ResponseDTO {

        @Schema(description = "가계부 id")
        private Long ledgerId;

        @Schema(description = "가계부 이름")
        private String ledgerName;

        @Schema(description = "가계부 owner 유저네임")
        private String ledgerOwnerUsername;

        public ResponseDTO(Long ledgerId, String ledgerName, String ledgerOwnerUsername) {
            this.ledgerId = ledgerId;
            this.ledgerName = ledgerName;
            this.ledgerOwnerUsername = ledgerOwnerUsername;
        }
    }

    @Getter
    @Setter
    @Schema(description = "가계부 관리자 권한 위임 DTO")
    public static class DelegateOwnerDTO {

        @Schema(description = "새로운 관리자 username")
        private String username;

    }

}