package com.example.demo.dto;

import com.example.demo.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "하위 카테고리 추가 요청 DTO")
public class SubCategoryReqDto {
    @Schema(description = "카테고리 이름")
    @NotBlank(message = "카테고리 이름을 입력해야합니다.")
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
