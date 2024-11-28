package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Schema(description = "초대 요청/응답 DTO")
public class InviteDTO {

    @Getter
    @Setter
    @Schema(description = "초대 요청 DTO")
    public static class Request {
        @Schema(description = "초대할 멤버 id")
        private Long memberId;
    }

    @Getter
    @Setter
    @Schema(description = "초대 응답 DTO")
    public static class Response {
        @Schema(description = "수락/거절 : 응답은 (YES/NO) 만 사용")
        private ResponseAction action;
    }



}
