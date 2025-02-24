package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class InviteDTO {

    @Getter
    @Setter
    @Schema(description = "초대 요청 DTO")
    public static class InviteRequest {
        @Schema(description = "초대할 멤버 username")
        private String memberUsername;
    }

    @Getter
    @Setter
    @Schema(description = "초대 요청 조회 DTO")
    public static class InviteRequestData {
        @Schema(description = "초대 요청 id")
        private Long inviteId;

        @Schema(description = "초대 요청을 보낸 가계부 이름")
        private String ledgerName;

        public InviteRequestData(Long inviteId, String ledgerName) {
            this.inviteId = inviteId;
            this.ledgerName = ledgerName;
        }
    }

    @Getter
    @Setter
    @Schema(description = "초대 응답 DTO")
    public static class InviteResponse {
        @Schema(description = "수락/거절 : 응답은 (YES/NO) 만 사용")
        private ResponseAction action;
    }



}
