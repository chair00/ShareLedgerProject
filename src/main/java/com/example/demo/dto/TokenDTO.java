package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "기능 테스트용 토큰")
public class TokenDTO {

    @Schema(description = "토큰")
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
