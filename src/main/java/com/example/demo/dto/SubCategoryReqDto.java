package com.example.demo.dto;

import com.example.demo.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryReqDto {
    @NotBlank(message = "카테고리 이름을 입력해야합니다.")
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
