package com.example.demo.dto;

import com.example.demo.entity.RequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "가계부 가입 요청/응답 DTO")
public class JoinDTO {

    @Getter
    @Setter
    @Schema(description = "가계부 가입 요청 조회 DTO")
    public static class RequestData {

        @Schema(description = "가입 요청을 보낸 멤버 username")
        private String memberUsername;

        @Schema(description = "가입 요청을 보낸 멤버 이름")
        private String memberName;

        public RequestData(String memberUsername, String memberName) {
            this.memberUsername = memberUsername;
            this.memberName = memberName;
        }
    }

    @Getter
    @Setter
    @Schema(description = "가입 응답 DTO")
    public static class Response {
        @Schema(description = "수락/거절 : 응답은 (YES/NO) 만 사용")
        private ResponseAction action;
    }
}
