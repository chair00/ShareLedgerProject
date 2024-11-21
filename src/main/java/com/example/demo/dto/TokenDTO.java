package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "토큰 전달 DTO")
public class TokenDTO {

    @Schema(description = "토큰")
    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
