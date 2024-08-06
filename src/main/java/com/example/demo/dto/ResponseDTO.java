package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "메시지 기본 응답")
public class ResponseDTO {
    @Schema(description = "메시지")
    private String message;
}
