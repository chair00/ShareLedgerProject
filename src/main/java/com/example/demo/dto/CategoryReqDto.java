package com.example.demo.dto;

import com.example.demo.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class CategoryReqDto {
    @NotBlank(message = "카테고리 이름을 입력해야합니다.")
    private String name;

    @NotBlank(message = "카테고리 타입을 선택해야합니다.")
    private String type;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .type(type)
                .build();
    }
}
