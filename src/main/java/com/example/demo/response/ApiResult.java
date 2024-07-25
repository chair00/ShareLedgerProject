package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {
    private String message;
    private T data;

    @Builder
    public ApiResult(String message, T data, ApiExceptionEntity exception){
        this.message = message;
        this.data = data;
    }
}
