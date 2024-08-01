package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

@Schema(description = "응답 DTO")
public class ApiResult<T> {
    @Schema(description = "메시지")
    private String message;
    @Schema(description = "데이터")
    private T data;

    @Builder
    public ApiResult(String message, T data, ApiExceptionEntity exception){
        this.message = message;
        this.data = data;
    }
}
