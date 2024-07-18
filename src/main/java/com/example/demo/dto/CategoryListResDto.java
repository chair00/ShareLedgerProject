package com.example.demo.dto;

import com.example.demo.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor

@Schema(description = "카테고리 목록 응답 DTO")
public class CategoryListResDto {
    @Schema(description = "카테고리 이름")
    private String name;

    @Schema(description = "수입/지출")
    private String type;

    public CategoryListResDto(Category entity) {
        this.name = entity.getName();
        this.type = entity.getType();
    }
}
